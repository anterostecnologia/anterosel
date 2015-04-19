/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.el.api;


import java.util.Enumeration;
import java.util.Iterator;
import java.util.MissingResourceException;

import br.com.anteros.core.metadata.beans.FeatureDescriptor;
import br.com.anteros.core.resource.messages.AnterosBundle;

/**
 * Defines property resolution behavior on instances of AnterosBundle. This resolver
 * handles base objects of type AnterosBundle. It accepts any object as a property and
 * coerces it to a java.lang.String for invoking AnterosBundle.getObject(String). This
 * resolver is read only and will throw a {@link PropertyNotWritableException} if setValue is
 * called. ELResolvers are combined together using {@link CompositeELResolver}s, to define rich
 * semantics for evaluating an expression. See the javadocs for {@link ELResolver} for details.
 */
public class AnterosBundleELResolver extends ELResolver {
	/**
	 * If the base object is a AnterosBundle, returns the most general type that this resolver
	 * accepts for the property argument. Otherwise, returns null. Assuming the base is a
	 * AnterosBundle, this method will always return String.class.
	 * 
	 * @param context
	 *            The context of this evaluation.
	 * @param base
	 *            The bundle to analyze. Only bases of type AnterosBundle are handled by this
	 *            resolver.
	 * @return null if base is not a AnterosBundle; otherwise String.class.
	 */
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return isResolvable(base) ? String.class : null;
	}

	/**
	 * If the base object is a AnterosBundle, returns an Iterator containing the set of keys
	 * available in the AnterosBundle. Otherwise, returns null. The Iterator returned must contain
	 * zero or more instances of java.beans.FeatureDescriptor. Each info object contains information
	 * about a key in the AnterosBundle, and is initialized as follows:
	 * <ul>
	 * <li>displayName - The String key name</li>
	 * <li>name - Same as displayName property</li>
	 * <li>shortDescription - Empty string</li>
	 * <li>expert - false</li>
	 * <li>hidden - false</li>
	 * <li>preferred - true</li>
	 * </ul>
	 * In addition, the following named attributes must be set in the returned FeatureDescriptors:
	 * <ul>
	 * <li>{@link ELResolver#TYPE} - String.class.</li>
	 * <li>{@link ELResolver#RESOLVABLE_AT_DESIGN_TIME} - true</li>
	 * </ul>
	 * 
	 * @param context
	 *            The context of this evaluation.
	 * @param base
	 *            The bundle to analyze. Only bases of type AnterosBundle are handled by this
	 *            resolver.
	 * @return An Iterator containing zero or more (possibly infinitely more) FeatureDescriptor
	 *         objects, each representing a key in this bundle, or null if the base object is not a
	 *         AnterosBundle.
	 */
	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		if (isResolvable(base)) {
			final Enumeration<String> keys = ((AnterosBundle) base).getKeys();
			return new Iterator<FeatureDescriptor>() {
				public boolean hasNext() {
					return keys.hasMoreElements();
				}
				public FeatureDescriptor next() {
					FeatureDescriptor feature = new FeatureDescriptor();
					feature.setDisplayName(keys.nextElement());
					feature.setName(feature.getDisplayName());
					feature.setShortDescription("");
					feature.setExpert(true);
					feature.setHidden(false);
					feature.setPreferred(true);
					feature.setValue(TYPE, String.class);
					feature.setValue(RESOLVABLE_AT_DESIGN_TIME, true);
					return feature;
				}
				public void remove() {
					throw new UnsupportedOperationException("Cannot remove");
					
				}
			};
		}
		return null;
	}

	/**
	 * If the base object is an instance of AnterosBundle, return null, since the resolver is read
	 * only. If the base is AnterosBundle, the propertyResolved property of the ELContext object
	 * must be set to true by this resolver, before returning. If this property is not true after
	 * this method is called, the caller should ignore the return value.
	 * 
	 * @param context
	 *            The context of this evaluation.
	 * @param base
	 *            The bundle to analyze. Only bases of type AnterosBundle are handled by this
	 *            resolver.
	 * @param property
	 *            The name of the property to analyze.
	 * @return If the propertyResolved property of ELContext was set to true, then null; otherwise
	 *         undefined.
	 * @throws NullPointerException
	 *             if context is null
	 */
	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException("context is null");
		}
		if (isResolvable(base)) {
			context.setPropertyResolved(true);
		}
		return null;
	}

	/**
	 * If the base object is an instance of AnterosBundle, the provided property will first be
	 * coerced to a String. The Object returned by getObject on the base AnterosBundle will be
	 * returned. If the base is AnterosBundle, the propertyResolved property of the ELContext
	 * object must be set to true by this resolver, before returning. If this property is not true
	 * after this method is called, the caller should ignore the return value.
	 * 
	 * @param context
	 *            The context of this evaluation.
	 * @param base
	 *            The bundle to analyze. Only bases of type AnterosBundle are handled by this
	 *            resolver.
	 * @param property
	 *            The name of the property to analyze. Will be coerced to a String.
	 * @return If the propertyResolved property of ELContext was set to true, then null if property
	 *         is null; otherwise the Object for the given key (property coerced to String) from the
	 *         AnterosBundle. If no object for the given key can be found, then the String "???" +
	 *         key + "???".
	 * @throws NullPointerException
	 *             if context is null.
	 * @throws ELException
	 *             if an exception was thrown while performing the property or variable resolution.
	 *             The thrown exception must be included as the cause property of this exception, if
	 *             available.
	 */
	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException("context is null");
		}
		Object result = null;
		if (isResolvable(base)) {
			if (property != null) {
				try {
					result = ((AnterosBundle) base).getMessage(property.toString());
				} catch (MissingResourceException e) {
					result = "???" + property + "???";
				}
			}
			context.setPropertyResolved(true);
		}
		return result;
	}

	/**
	 * If the base object is not null and an instanceof AnterosBundle, return true.
	 * 
	 * @return If the propertyResolved property of ELContext was set to true, then true; otherwise
	 *         undefined.
	 * @throws NullPointerException
	 *             if context is null.
	 */
	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException("context is null");
		}
		if (isResolvable(base)) {
			context.setPropertyResolved(true);
		}
		return true;
	}

	/**
	 * If the base object is a AnterosBundle, throw a {@link PropertyNotWritableException}.
	 * 
	 * @param context
	 *            The context of this evaluation.
	 * @param base
	 *            The bundle to analyze. Only bases of type AnterosBundle are handled by this
	 *            resolver.
	 * @param property
	 *            The name of the property to analyze. Will be coerced to a String.
	 * @param value
	 *            The value to be set.
	 * @throws NullPointerException
	 *             if context is null.
	 * @throws PropertyNotWritableException
	 *             Always thrown if base is an instance of AnterosBundle.
	 */
	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		if (context == null) {
			throw new NullPointerException("context is null");
		}
		if (isResolvable(base)) {
			throw new PropertyNotWritableException("resolver is read-only");
		}
	}

	/**
	 * Test whether the given base should be resolved by this ELResolver.
	 * 
	 * @param base
	 *            The bean to analyze.
	 * @param property
	 *            The name of the property to analyze. Will be coerced to a String.
	 * @return base instanceof AnterosBundle
	 */
	private final boolean isResolvable(Object base) {
		return base instanceof AnterosBundle;
	}
}
