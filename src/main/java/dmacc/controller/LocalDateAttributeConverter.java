package dmacc.controller;

import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;



/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * Feb 20, 2022
 */




@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		// TODO Auto-generated method stub
		return (attribute == null ? null: Date.valueOf(attribute));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		// TODO Auto-generated method stub
		return (dbData == null ? null: dbData.toLocalDate());
	}


}