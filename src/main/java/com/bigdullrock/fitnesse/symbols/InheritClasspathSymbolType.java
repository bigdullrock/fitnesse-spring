package com.bigdullrock.fitnesse.symbols;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import fitnesse.html.HtmlUtil;
import fitnesse.wikitext.parser.*;

public class InheritClasspathSymbolType extends SymbolType implements Translation, PathsProvider {

  public InheritClasspathSymbolType() {
    super(InheritClasspathSymbolType.class.getName());
    wikiMatcher(new Matcher().startLineOrCell().string("!inheritClasspath"));
  }

  @Override
  public String toTarget(Translator translator, Symbol symbol) {
    return getClasspathElements().stream()
        .map(s -> metaText("classpath: " + s))
        .collect(Collectors.joining(HtmlUtil.BR.html()));
  }

  @Override
  public Collection<String> providePaths(Translator translator, Symbol symbol) {
    return getClasspathElements();
  }

  private List<String> getClasspathElements() {
    return Arrays.asList(System.getProperty("java.class.path").split(":"))
        .stream()
        .filter(s -> !StringUtils.contains(s, " "))
        .collect(Collectors.toList());
  }

  public static String metaText(String text) {
    return "<span class=\"meta\">" + text + "</span>";
  }
}
