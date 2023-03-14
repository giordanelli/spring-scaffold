package dev.graffa.scaffold.language;

import java.util.List;

public class JavaMethod {
    protected String visibility = VISIBILITY.PACKAGE.name, returnObject, name;
    protected List<JavaParameter> parameters;
    protected List<String> annotations;

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    protected List<String> instructions;

    public JavaMethod(String visibility, String returnObject, String name, List<JavaParameter> parameters,
                      List<String> instructions) {
        this.visibility = visibility;
        this.returnObject = returnObject;
        this.name = name;
        this.parameters = parameters;
        this.instructions=instructions;
    }

    public JavaMethod(String returnObject, String name, List<JavaParameter> parameters, List<String> instructions) {
        this.returnObject = returnObject;
        this.name = name;
        this.parameters = parameters;
        this.instructions=instructions;
    }

    public JavaMethod(String returnObject, String name, List<String> instructions) {
        this.returnObject = returnObject;
        this.name = name;
        this.instructions=instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(String returnObject) {
        this.returnObject = returnObject;
    }

    public List<JavaParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<JavaParameter> parameters) {
        this.parameters = parameters;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public enum VISIBILITY {
        PUBLIC("public"),
        PRIVATE("private"),
        PROTECTED("protected"),
        PACKAGE("");

        public final String name;

        VISIBILITY(String label) {
            this.name = label;
        }
    }
}
