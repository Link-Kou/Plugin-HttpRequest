package com.linkkou.httprequest.processor;


import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.Pair;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.*;

/**
 * 基于JDK动态代码生成
 *
 * @author LK
 * @date 2018-05-31 10:46
 */
@SupportedAnnotationTypes({"com.linkkou.httprequest.processor.HTTPRequestTest"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class HTTPProcessor extends AbstractProcessor {

    private static final String ANNOTATION = "@" + HTTPRequestTest.class.getSimpleName();
    private Trees trees;
    private TreeMaker make;
    private Name.Table names;
    private Context context;
    private Elements mElementsUtils;

    /**
     * 初始化
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementsUtils = processingEnv.getElementUtils();
        trees = Trees.instance(processingEnv);
        context = ((JavacProcessingEnvironment)
                processingEnv).getContext();
        make = TreeMaker.instance(context);
        names = Names.instance(context).table;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 依据相关注解解析
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(HTTPRequestTest.class.getCanonicalName());
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //从注解中找到所有的类,以及对应的注解的方法
        List<TypeElement> targetClassMap = findAnnoationElement(roundEnv);
        //生成每一个类和方法
        for (TypeElement item : targetClassMap) {
            //获取包名
            String packageName = mElementsUtils.getPackageOf(item).getQualifiedName().toString();
            //获取类名称
            String className = item.getSimpleName().toString();

            JCTree tree = (JCTree) trees.getTree(item);
            TreeTranslator visitor = new Inliner();
            tree.accept(visitor);
        }
        return true;
    }


    /**
     * 查询所有带有{@link HTTPRequestTest HTTPRequestTest注解的}
     *
     * @param roundEnvironment
     * @return
     */
    private List<TypeElement> findAnnoationElement(RoundEnvironment roundEnvironment) {
        List<TypeElement> targetClassMap = new ArrayList<>();
        //找到所有跟AnDataCollect注解相关元素
        Collection<? extends Element> anLogSet = roundEnvironment.getElementsAnnotatedWith(HTTPRequestTest.class);
        //遍历所有元素
        for (Element e : anLogSet) {
            if (e.getKind() != ElementKind.FIELD) {
                continue;
            }
            //此处找到的是类的描述类型，因为我们的AnDataCollect的注解描述是method的所以closingElement元素是类
            TypeElement enclosingElement = (TypeElement) e.getEnclosingElement();

            //对类做一个缓存
            targetClassMap.add(enclosingElement);
        }
        return targetClassMap;
    }

    /**
     * 代码树结构修改
     */
    private class Inliner extends TreeTranslator {

        /**
         * 变量的处理
         *
         * @param var1
         */
        @Override
        public void visitVarDef(JCTree.JCVariableDecl var1) {
            super.visitVarDef(var1);

            if (var1.mods.getAnnotations() == null || var1.mods.getAnnotations().size() == 0) {
                return;
            }
            String HTTPRequestTestVaule = "";
            for (JCTree.JCAnnotation jcAnnotation : var1.mods.getAnnotations()) {
                if (!jcAnnotation.getAnnotationType().type.toString().equals(HTTPRequestTest.class.getName())) {
                    return;
                } else {
                    for (Pair<Symbol.MethodSymbol, Attribute> pair : jcAnnotation.attribute.values) {
                        HTTPRequestTestVaule = (String) pair.snd.getValue();
                    }
                }
            }
            //变量的调用new方法
            JCTree.JCExpression loggerNewClass = make.NewClass(null,
                    null,
                    //类名称 会自己导入包
                    make.Select(
                            make.Select(
                                    make.Select(make.Select(make.Ident(names.fromString("com")), names.fromString("linkkou"))
                                            , names.fromString("httprequest")
                                    )
                                    , names.fromString("processor")
                            )
                            , names.fromString("HTTPProxyInstance")
                    ),
                    //参数
                    com.sun.tools.javac.util.List.of(
                            make.Select(var1.vartype, names.fromString("class")),
                            make.Literal(HTTPRequestTestVaule)
                    ),
                    null);


            JCTree.JCExpression loggerType2 = make.Select(
                    //make.Ident(names.fromString("TestInterface")) ->  (TestInterface) ***
                    loggerNewClass,
                    names.fromString("getProxy"));

            JCTree.JCMethodInvocation getLoggerCall = make.Apply(
                    com.sun.tools.javac.util.List.nil(),
                    //构建 -> getProxy()
                    loggerType2,
                    //参数
                    com.sun.tools.javac.util.List.nil());

            JCTree.JCVariableDecl jcv = make.VarDef(
                    var1.getModifiers(),
                    var1.name,
                    var1.vartype,
                    //构建 -> (TestInterface) ***
                    make.TypeCast(var1.vartype, getLoggerCall)
            );
            this.result = jcv;
        }
    }
}