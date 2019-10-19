package cn.web.auction.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String time){
		if(time=="") return null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
