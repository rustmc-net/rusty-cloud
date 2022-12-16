package net.rustmc.cloud.base.objects;

public class PrimitiveGroup {

    public String name;
    public boolean template;

    private PrimitiveGroup(String name, boolean template) {
        this.name = name;
        this.template = template;
    }

    public static PrimitiveGroup group(String name, boolean template) {
        return new PrimitiveGroup(name, template);
    }

}
