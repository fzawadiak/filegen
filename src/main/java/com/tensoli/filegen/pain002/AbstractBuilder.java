package com.tensoli.filegen.pain002;

import java.io.Reader;
import java.io.Writer;

public abstract class AbstractBuilder {
	public abstract void parse(Reader in, Writer out) throws Exception;
}
