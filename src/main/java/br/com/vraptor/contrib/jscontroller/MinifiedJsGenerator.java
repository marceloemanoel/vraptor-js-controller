package br.com.vraptor.contrib.jscontroller;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSSourceFile;

public class MinifiedJsGenerator implements JsGenerator {
  
  private JsGenerator generator;

  public MinifiedJsGenerator(JsGenerator generator){
    this.generator = generator;
  }
  
  @Override
  public String generate(Controller controller) {
    String javascriptCode = generator.generate(controller);
    
    Compiler compiler = new Compiler();
    CompilerOptions options = new CompilerOptions();
    CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

    JSSourceFile input = JSSourceFile.fromCode("input.js", javascriptCode);
    compiler.compile(JSSourceFile.fromCode("externs.js",""), input, options);
    
    return compiler.toSource();
  }
  
}
