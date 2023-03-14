package dev.graffa.scaffold.language;

public class JavaParameter {

    protected String type, name,annotation;

    public JavaParameter(String type, String name,String annotation) {
        this.type = type;
        this.name = name;
        this.annotation=annotation;
    }

    public JavaParameter(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
