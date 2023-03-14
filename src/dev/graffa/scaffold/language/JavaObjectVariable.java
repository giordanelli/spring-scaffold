package dev.graffa.scaffold.language;

public class JavaObjectVariable {

    protected String visibility, type, name, annotation;

    public JavaObjectVariable(String visibility, String type, String name, String annotation) {
        this.visibility = visibility;
        this.type = type;
        this.name = name;
        this.annotation = annotation;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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
