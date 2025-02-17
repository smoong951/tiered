package com.stereowalker.tiered.mixin;

import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Multimap;
import com.stereowalker.tiered.Tiered;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "forEachModifier", at = @At("TAIL"))
    private void go(EquipmentSlot slot, BiConsumer<Holder<Attribute>, AttributeModifier> pAction, CallbackInfo ci) {
    	ItemStack thisStack = (ItemStack)(Object)this;
    	Tiered.AppendAttributesToOriginal(thisStack, slot, Tiered.isPreferredEquipmentSlot(thisStack, slot), "AttributeModifiers",
				template -> template.getRequiredEquipmentSlot(), 
				template -> template.getOptionalEquipmentSlot(), 
				(template) -> template.realize(pAction, slot));
    }
}
