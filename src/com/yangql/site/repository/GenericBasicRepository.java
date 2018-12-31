package com.yangql.site.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.yangql.site.interfaceClasses.GenericRepository;

public abstract class GenericBasicRepository<I extends Serializable, E extends Serializable> implements GenericRepository<I, E> {
	protected final Class<I> idClass;
    protected final Class<E> entityClass;
    
    @SuppressWarnings("unchecked")
    public GenericBasicRepository()
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        while(!(genericSuperclass instanceof ParameterizedType))
        {
            if(!(genericSuperclass instanceof Class))
                throw new IllegalStateException("Unable to determine type " +
                        "arguments because generic superclass neither " +
                        "parameterized type nor class.");
            if(genericSuperclass == GenericBasicRepository.class)
                throw new IllegalStateException("Unable to determine type " +
                        "arguments because no parameterized generic superclass " +
                        "found.");

            genericSuperclass = ((Class<?>)genericSuperclass).getGenericSuperclass();
        }

        ParameterizedType type = (ParameterizedType)genericSuperclass;
        Type[] arguments = type.getActualTypeArguments();
        this.idClass = (Class<I>)arguments[0];
        this.entityClass = (Class<E>)arguments[1];
    }
}
