package br.com.anteros.el.resource.messages;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import br.com.anteros.core.resource.messages.AnterosBundle;

public class AnterosELMessages implements AnterosBundle {

	private final Map<String, String> messages = new HashMap<String, String>();

	public AnterosELMessages() {
		messages.put("message.unknown", "Unknown message");

		messages.put("error.identifier.property.notfound", "Cannot resolve identifier ''{0}''");
		messages.put("error.identifier.method.notfound", "Cannot resolve method for identifier ''{0}'' (null)");
		messages.put("error.identifier.method.returntype", "Return type ''{0}'' of method for identifier ''{1}'' does not match ''{2}''");
		messages.put("error.identifier.method.notamethod", "Cannot resolve method for identifier ''{0}'' (found {1} instead)");
		messages.put("error.identifier.method.access", "Cannot access method ''{0}''");
		messages.put("error.identifier.method.invocation", "Error invoking method ''{0}'': {1}");
		messages.put("error.property.base.null", "Target unreachable, base expression ''{0}'' resolved to null");
		messages.put("error.property.property.notfound", "Cannot resolve property ''{0}'' in ''{1}''");
		messages.put("error.property.method.notfound", "Cannot find method ''{0}'' in ''{1}''");
		messages.put("error.property.method.returntype", "Return type ''{0}'' of method ''{1}'' in ''{2}'' does not match ''{3}''");
		messages.put("error.property.method.resolve", "Cannot resolve method ''{0}'' in ''{1}''");
		messages.put("error.property.method.access", "Cannot access method ''{0}'' in ''{1}''");
		messages.put("error.property.method.invocation", "Error invoking method ''{0}'' in ''{1}''");
		messages.put("error.function.invocation", "Error invoking function ''{0}''");
		messages.put("error.function.access", "Cannot access function ''{0}''");
		messages.put("error.function.nomapper", "Expression uses functions, but no function mapper was provided");
		messages.put("error.function.notfound", "Could not resolve function ''{0}''");
		messages.put("error.function.params", "Parameters for function ''{0}'' do not match");

		messages.put("error.method.literal.void", "Expected type ''void'' is not allowed for literal method expression ''{0}''");
		messages.put("error.method.invalid", "Expression ''{0}'' is not a valid method expression");
		messages.put("error.method.notypes", "Parameter types must not be null");
		messages.put("error.value.set.rvalue", "Cannot set value of a non-lvalue expression ''{0}''");
		messages.put("error.value.notype", "Expected type must not be null");
		messages.put("error.compare.types", "Cannot compare ''{0}'' and ''{1}''");
		messages.put("error.coerce.type", "Cannot coerce ''{0}'' of {1} to {2} (incompatible type)");
		messages.put("error.coerce.value", "Cannot coerce ''{0}'' of {1} to {2} (incompatible value)");
		messages.put("error.negate", "Cannot negate ''{0}''");
		messages.put("error.null", "Expression cannot be null");

		messages.put("error.scan", "lexical error at position {0}, encountered {1}, expected {2}");
		messages.put("error.parse", "syntax error at position {0}, encountered {1}, expected {2}");
		messages.put("error.build", "Error parsing ''{0}'': {1}");

		messages.put("error.config.builder", "Error creating builder: {1}");

	}

	public String getMessage(String key) {
		return messages.get(key);
	}


	public String getMessage(String key, Object... parameters) {
		return MessageFormat.format(getMessage(key), parameters);
	}

	public Enumeration<String> getKeys() {
		return new Vector<String>(messages.keySet()).elements();
	}

}
