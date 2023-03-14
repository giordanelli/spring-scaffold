package dev.graffa.scaffold.language;

import java.util.List;

public class JavaFile {
    protected String name, packageName, superClassName, interfaceName;
    protected List<String> imports;

    protected List<String> generics;
    protected List<String> annotations;
    protected TYPE type=TYPE.Class;

    protected List<JavaMethod> methods;

    protected List<JavaObjectVariable> variables;

    public List<JavaObjectVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<JavaObjectVariable> variables) {
        this.variables = variables;
    }

    public List<JavaMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<JavaMethod> methods) {
        this.methods = methods;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public List<String> getGenerics() {
        return generics;
    }

    public void setGenerics(List<String> generics) {
        this.generics = generics;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public enum TYPE {
        Class("class"),
        Interface("interface");

        public final String name;

        TYPE(String label) {
            this.name = label;
        }
    }

}

