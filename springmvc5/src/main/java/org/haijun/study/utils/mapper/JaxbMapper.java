package org.haijun.study.utils.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.reflections.Reflections;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JaxbMapper {

    private static ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<>();

    public JaxbMapper() {
    }

    public static String toXml(Object root) {
        Class clazz = getUserClass(root);
        return toXml((Object)root, (Class)clazz, (String)null);
    }

    public static String toXml(Object root, String encoding) {
        Class clazz = getUserClass(root);
        return toXml(root, clazz, encoding);
    }

    public static String toXml(Object root, Class clazz, String encoding) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(clazz, encoding).marshal(root, writer);
            return writer.toString();
        } catch (JAXBException var4) {
            throw unchecked(var4);
        }
    }

    public static String toXml(Collection<?> root, String rootName, Class clazz) {
        return toXml(root, rootName, clazz, (String)null);
    }

    public static String toXml(Collection<?> root, String rootName, Class clazz, String encoding) {
        try {
            JaxbMapper.CollectionWrapper wrapper = new JaxbMapper.CollectionWrapper();
            wrapper.collection = root;
            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement(new QName(rootName), JaxbMapper.CollectionWrapper.class, wrapper);
            StringWriter writer = new StringWriter();
            createMarshaller(clazz, encoding).marshal(wrapperElement, writer);
            return writer.toString();
        } catch (JAXBException var7) {
            throw unchecked(var7);
        }
    }

    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            StringReader reader = new StringReader(xml);
            return (T)(createUnmarshaller(clazz).unmarshal(reader));
        } catch (JAXBException var3) {
            throw unchecked(var3);
        }
    }

    public static Marshaller createMarshaller(Class clazz, String encoding) {
        try {
            JAXBContext jaxbContext = getJaxbContext(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            if (StringUtils.isNotBlank(encoding)) {
                marshaller.setProperty("jaxb.encoding", encoding);
            }

            return marshaller;
        } catch (JAXBException var4) {
            throw unchecked(var4);
        }
    }

    public static Unmarshaller createUnmarshaller(Class clazz) {
        try {
            JAXBContext jaxbContext = getJaxbContext(clazz);
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException var2) {
            throw unchecked(var2);
        }
    }

    protected static JAXBContext getJaxbContext(Class clazz) {
        Validate.notNull(clazz, "'clazz' must not be null", new Object[0]);
        JAXBContext jaxbContext = (JAXBContext)jaxbContexts.get(clazz);
        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(clazz, JaxbMapper.CollectionWrapper.class);
                jaxbContexts.putIfAbsent(clazz, jaxbContext);
            } catch (JAXBException var3) {
                throw new RuntimeException("Could not instantiate JAXBContext for class [" + clazz + "]: " + var3.getMessage(), var3);
            }
        }

        return jaxbContext;
    }

    public static class CollectionWrapper {
        @XmlAnyElement
        protected Collection<?> collection;

        public CollectionWrapper() {
        }
    }

    public static Class<?> getUserClass(Object instance) {
        Validate.notNull(instance, "Instance must not be null", new Object[0]);
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains("$$")) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }

        return clazz;
    }

    public static RuntimeException unchecked(Throwable ex) {
        return ex instanceof RuntimeException ? (RuntimeException)ex : new RuntimeException(ex);
    }
}
