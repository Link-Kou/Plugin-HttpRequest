package com.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import retrofit2.Response;
import com.plugin.httprequest.extendPlugin.HttpConversion;

import javax.persistence.EntityNotFoundException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * 自定义HTTP返回值转换
 * @author LK
 * @version 1.0
 * @data 2017-12-13 19:04
 */
public class HttpReturnConversion implements HttpConversion {

	/**
	 * GSON json 输出
	 */
	public static Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();

	/**
	 * 转换获取数据转换为Model
	 * @param response
	 * @param type
	 * @return
	 */
	@Override
	public Object getModel(Response<byte[]> response, Type type) {
		if(type != null){
			try {
				String returnbody = new String(response.body(),"utf-8");
				return gson.fromJson(returnbody,type);
			} catch (Exception e) {
				type = null;
				e.printStackTrace();
			}
		}
		return Optional.ofNullable(type)
				.orElseThrow(() -> new EntityNotFoundException("HTTP HttpReturnConversion Data The Unknown"));
	}

	/**
	 * 输入gosn
	 * <pre>
	 *  try {
	 *      if(((Class) type).getName().equals("java.lang.String")){
	 *          for (char c : String.valueOf(o).toCharArray()){
	 *             writer.append(c);
	 *           }
	 *          writer.close();
	 *      }else{
	 *          TypeAdapter adapter = gson.getAdapter(TypeToken.get(type));
	 *          JsonWriter jsonWriter =  gson.newJsonWriter(writer);
	 *          adapter.write(jsonWriter,o);
	 *          jsonWriter.close();
	 *          }
	 *      } catch (Exception e) {
	 *      e.printStackTrace();
	 *   }
	 *
	 * </pre>
	 *
	 * @param writer
	 * @param o
	 * @param type
	 */
	@Override
	public void bodyWriter(Writer writer, Object o, Type type) {
		try {
			TypeAdapter adapter = gson.getAdapter(TypeToken.get(type));
			JsonWriter jsonWriter =  gson.newJsonWriter(writer);
			adapter.write(jsonWriter,o);
			jsonWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}