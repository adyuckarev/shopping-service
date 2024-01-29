package com.example.shoppingservice.util;

import com.example.shoppingservice.dto.xml.UserPurchaseInfoType;
import com.example.shoppingservice.exception.NotFoundException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;

@UtilityClass
public class ValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private static final String XSD_SCHEMA_PATH = "xsd/test.xsd";

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static boolean isValid(UserPurchaseInfoType userPurchaseInfoType) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserPurchaseInfoType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(ValidationUtil.class.getClassLoader().getResource(XSD_SCHEMA_PATH));
            unmarshaller.setSchema(schema);
            unmarshaller.unmarshal(new StreamSource(new StringReader(ConvertUtil.convertToXml(userPurchaseInfoType))));
            return true;
        } catch (JAXBException | SAXException e) {
            logger.error(String.valueOf(e.getCause()));
            return false;
        }
    }

    public static String handleValidationErrors(BindingResult bindingResult, HttpServletResponse response, Model model, String viewName, Object entity) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                logger.warn("Validation error in field '{}': {}", error.getField(), error.getDefaultMessage());
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            model.addAttribute("userPurchase", entity);
            return viewName;
        }
        return "";
    }

    public static String handleBindingErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Invalid XML format. Errors: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                String fieldErrorMessage = String.format("%s: %s; ", error.getField(), error.getDefaultMessage());
                errorMessage.append(fieldErrorMessage);
                logger.warn(fieldErrorMessage);
            }
            return errorMessage.toString();
        }
        return "";
    }
}
