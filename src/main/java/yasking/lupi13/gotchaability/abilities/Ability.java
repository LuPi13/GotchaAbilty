package yasking.lupi13.gotchaability.abilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import yasking.lupi13.gotchaability.Functions;

import java.util.List;

public class Ability {
    private String name;
    private String codename;
    private String grade;
    private Material material;
    private List<String> lore;
    private Material offHandMaterial;
    private List<String> offHandLore;


    Ability(String name, String codename, String grade, Material material, List<String> lore) {
        this.name = name;
        this.codename = codename;
        this.grade = grade;
        this.material = material;
        this.lore = lore;
    }
    Ability(String name, String codename, String grade, Material material, List<String> lore, Material offHandMaterial, List<String> offHandLore) {
        this.name = name;
        this.codename = codename;
        this.grade = grade;
        this.material = material;
        this.lore = lore;
        this.offHandMaterial = offHandMaterial;
        this.offHandLore = offHandLore;
    }

    public String getCodeName() {
        return this.codename;
    }

    public String getGrade() {
        return this.grade;
    }

    public ItemStack getItem() {
        return Functions.makeDisplayItem(this.material, Functions.makeDisplayName(this.name, this.grade), this.lore);
    }

    public ItemStack getOffHandItem() {
        return Functions.makeDisplayItem(this.offHandMaterial, Functions.makeDisplayName(this.name, this.grade), this.offHandLore);
    }
}
