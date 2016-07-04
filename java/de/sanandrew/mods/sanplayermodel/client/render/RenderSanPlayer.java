/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.sanplayermodel.client.render;

import com.google.gson.Gson;
import de.sanandrew.mods.sanplayermodel.SanPlayerModel;
import de.sanandrew.mods.sanplayermodel.client.model.ModelBoxBuilder;
import de.sanandrew.mods.sanplayermodel.client.model.ModelSanPlayer;
import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//@SideOnly( Side.CLIENT )
public class RenderSanPlayer
        extends RenderPlayer
        implements IResourceManagerReloadListener
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("sanplayermodel", "textures/entity/player/SanPlayer.png");
    public static final ResourceLocation TEXTURE_SLEEP = new ResourceLocation("sanplayermodel", "textures/entity/player/SanPlayer_sleeping.png");

    private ModelSanPlayer myModel = new ModelSanPlayer(0.0F, false);
    private ModelSanPlayer myModelArmor = new ModelSanPlayer(0.05F, true);

//    private Map<String, ColorObject> unknownTextureColorMap = new HashMap<>();
//    private Map<String, CubeLoader> hatRenderList = new HashMap<>();

    public RenderSanPlayer(RenderManager manager) {
        super(manager);
        this.mainModel = this.myModel;
//        this.modelBipedMain = this.myModel;
//        this.modelArmorChestplate = new ModelBiped();
//        this.modelArmor = new ModelBiped();
    }

//    @Override
//    protected int shouldRenderPass(AbstractClientPlayer player, int renderPass, float partTicks) {
//        this.myModel.skirt1.showModel = !(player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() instanceof ItemArmor);
//        this.myModel.skirt2.showModel = this.myModel.skirt1.showModel;
//
//        this.myModel.armRight2.showModel = !(player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() instanceof ItemArmor);
//        this.myModel.armLeft2.showModel = this.myModel.armRight2.showModel;
//
//        ItemStack stack = player.inventory.armorItemInSlot(3 - renderPass);
//
//        if( renderPass == 0 ) {
//            this.myModel.hideTails = false;
//        }
//
//        if( ItemStackUtils.isValidStack(stack) ) {
//            Item item = stack.getItem();
//            this.myModelArmor.hatBase = null;
//
//            if( item instanceof ItemArmor ) {
//                ItemArmor armorItem = (ItemArmor) item;
//                String unlocName = stack.getUnlocalizedName().replace(':', '_');
//
//                switch( renderPass ) {
//                    case 0:
//                        this.bindTexture(tryLoadArmorPiece("Hat", player, stack, renderPass));
//                        if( !this.hatRenderList.containsKey(unlocName) ) {
//                            CubeLoader cubes = CubeLoader.loadFromResource("model/hats/" + unlocName);
//                            if( cubes == null ) {
//                                cubes = CubeLoader.loadFromResource("model/hats/item.helmetDefault");
//                                if( cubes == null ) {
//                                    cubes = new CubeLoader();
//                                }
//                            }
//                            cubes.initCubeInstances(this.myModelArmor);
//                            hatRenderList.put(unlocName, cubes);
//                        }
//
//                        this.myModelArmor.hatBase = this.hatRenderList.get(unlocName).getCubeParent();
//                        this.myModel.hideTails = this.hatRenderList.get(unlocName).hideTails;
//                        break;
//                    case 1:
//                        this.bindTexture(tryLoadArmorPiece("Chest", player, stack, renderPass));
//                        break;
//                    case 2:
//                        this.bindTexture(tryLoadArmorPiece("Leggings", player, stack, renderPass));
//                        break;
//                    case 3:
//                        this.bindTexture(tryLoadArmorPiece("Boots", player, stack, renderPass));
//                        break;
//                }
//
////                myModelArmor.hatBase.showModel = renderPass == 0;
//                myModelArmor.body.showModel = renderPass == 1 || renderPass == 2;
//                myModelArmor.bipedLeftArm.showModel = renderPass == 1;
//                myModelArmor.armLeft2.showModel = renderPass == 1;
//                myModelArmor.bipedRightArm.showModel = renderPass == 1;
//                myModelArmor.armRight2.showModel = renderPass == 1;
//                myModelArmor.skirt1.showModel = renderPass == 2;
//                myModelArmor.skirt2.showModel = renderPass == 2;
//                myModelArmor.legLeft.showModel = renderPass == 2 || renderPass == 3;
//                myModelArmor.legRight.showModel = renderPass == 2 || renderPass == 3;
//                this.setRenderPassModel(myModelArmor);
//                myModelArmor.onGround = this.myModel.onGround;
//                myModelArmor.isRiding = this.myModel.isRiding;
//                myModelArmor.isChild = this.myModel.isChild;
//                myModelArmor.isSneak = this.myModel.isSneak;
//                myModelArmor.aimedBow = this.myModel.aimedBow;
//                myModelArmor.heldItemLeft = this.myModel.heldItemLeft;
//                myModelArmor.heldItemRight = this.myModel.heldItemRight;
//
//                int armorColor = armorItem.getColor(stack);
//                if( armorColor != -1 ) {
//                    float red = (armorColor >> 16 & 255) / 255.0F;
//                    float green = (armorColor >> 8 & 255) / 255.0F;
//                    float blue = (armorColor & 255) / 255.0F;
//                    GL11.glColor3f(red, green, blue);
//                } else if( this.unknownTextureColorMap.containsKey(unlocName) ) {
//                    ColorObject rgba = this.unknownTextureColorMap.get(unlocName);
//                    GL11.glColor3f(rgba.getRed() / 255.0F, rgba.getGreen() / 255.0F, rgba.getBlue() / 255.0F);
//                } else {
//                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
//                }
//
//                if( stack.isItemEnchanted() ) {
//                    return 15;
//                }
//
//                return 1;
//            }
//        }
//
//        return -1;
//    }

//    @Override
//    protected void renderEquippedItems(AbstractClientPlayer player, float partTicks) {
//        super.renderEquippedItems(player, partTicks);
//
//        GL11.glPushMatrix();
//        this.myModel.body.postRender(0.0625F);
//        ItemStack slot = player.inventory.getStackInSlot(0);
//        if( slot != null && slot != player.getCurrentEquippedItem() ) {
//            GL11.glPushMatrix();
//            GL11.glRotatef(-80.0F, 0.0F, 0.0F, 1.0F);
//            GL11.glScalef(0.6F, 0.6F, 0.6F);
//            GL11.glTranslatef(-1.0F, -0.4F, 0.3F);
//            ItemRenderHelper.renderItemIn3D(slot);
//            GL11.glPopMatrix();
//            GL11.glTranslatef(0.0F, 0.0F, 0.05F);
//        }
//
//        slot = player.inventory.getStackInSlot(1);
//        if( slot != null && slot != player.getCurrentEquippedItem() ) {
//            GL11.glPushMatrix();
//            GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
//            GL11.glScalef(0.6F, 0.6F, 0.6F);
//            GL11.glTranslatef(-0.6F, -0.0F, 0.3F);
//            ItemRenderHelper.renderItemIn3D(slot);
//            GL11.glPopMatrix();
//        }
//        GL11.glPopMatrix();
//    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractClientPlayer clientPlayer) {
        return clientPlayer.isPlayerSleeping() ? TEXTURE_SLEEP : TEXTURE;
    }

    @Override
    public void renderRightArm(AbstractClientPlayer clientPlayer) {
        this.bindTexture(this.getEntityTexture(clientPlayer));
        float f = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float f1 = 0.0625F;
        ModelSanPlayer modelplayer = this.myModel;
        this.setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.armRight2.rotateAngleX = 0.0F;
        modelplayer.armRight2.render(0.0625F);
        GlStateManager.disableBlend();
    }

    @Override
    public void renderLeftArm(AbstractClientPlayer clientPlayer) {
        this.bindTexture(this.getEntityTexture(clientPlayer));
        float f = 1.0F;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        float f1 = 0.0625F;
        ModelSanPlayer modelplayer = this.myModel;
        this.setModelVisibilities(clientPlayer);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.armLeft2.rotateAngleX = 0.0F;
        modelplayer.armLeft2.render(0.0625F);
        GlStateManager.disableBlend();
    }

    /** from super class **/
    private void setModelVisibilities(AbstractClientPlayer clientPlayer)
    {
        ModelPlayer modelplayer = this.getMainModel();

        if (clientPlayer.isSpectator())
        {
            modelplayer.setInvisible(false);
            modelplayer.bipedHead.showModel = true;
            modelplayer.bipedHeadwear.showModel = true;
        }
        else
        {
            ItemStack itemstack = clientPlayer.getHeldItemMainhand();
            ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
            modelplayer.setInvisible(true);
            modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
            modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
            modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
            modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
            modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            modelplayer.isSneak = clientPlayer.isSneaking();
            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

            if (itemstack != null)
            {
                modelbiped$armpose = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0)
                {
                    EnumAction enumaction = itemstack.getItemUseAction();

                    if (enumaction == EnumAction.BLOCK)
                    {
                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                    }
                    else if (enumaction == EnumAction.BOW)
                    {
                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }

            if (itemstack1 != null)
            {
                modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0)
                {
                    EnumAction enumaction1 = itemstack1.getItemUseAction();

                    if (enumaction1 == EnumAction.BLOCK)
                    {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                    }
                }
            }

            if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
            {
                modelplayer.rightArmPose = modelbiped$armpose;
                modelplayer.leftArmPose = modelbiped$armpose1;
            }
            else
            {
                modelplayer.rightArmPose = modelbiped$armpose1;
                modelplayer.leftArmPose = modelbiped$armpose;
            }
        }
    }

    //    @Override
//    public void renderFirstPersonArm(EntityPlayer player) {
//        GL11.glColor3f(1.0F, 1.0F, 1.0F);
//        this.modelBipedMain.onGround = 0.0F;
//        boolean isRidingPrev = this.modelBipedMain.isRiding;
//        this.modelBipedMain.isRiding = false;
//        this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
//        this.modelBipedMain.isRiding = isRidingPrev;
//        if( this.renderManager.renderEngine != null ) {
//            if( ItemStackUtils.isValidStack(player.getCurrentArmor(2)) && player.getCurrentArmor(2).getItem() instanceof ItemArmor ) {
//                this.bindTexture(this.getEntityTexture(player));
//                this.myModel.bipedRightArm.render(0.0625F);
//                boolean prevArmR2Visible = this.myModel.armRight2.showModel;
//
//                Minecraft.getMinecraft().getTextureManager().bindTexture(tryLoadArmorPiece("Chest", player, player.getCurrentArmor(2), 1));
//
//                GL11.glPushMatrix();
//                GL11.glScalef(1.05F, 1.05F, 1.05F);
//                GL11.glTranslatef(0.015F, 0.00F, 0.0F);
//                this.myModel.bipedRightArm.render(0.0625F);
//                int armorColor = ((ItemArmor) player.getCurrentArmor(2).getItem()).getColor(player.getCurrentArmor(2));
//                String armoredChest = player.getCurrentArmor(2).getUnlocalizedName().replace(':', '_');
//
//                if( armorColor != -1 ) {
//                    float red = (armorColor >> 16 & 255) / 255.0F;
//                    float green = (armorColor >> 8 & 255) / 255.0F;
//                    float blue = (armorColor & 255) / 255.0F;
//                    GL11.glColor3f(red, green, blue);
//                } else if( this.unknownTextureColorMap.containsKey(armoredChest) ) {
//                    ColorObject rgba = this.unknownTextureColorMap.get(armoredChest);
//                    GL11.glColor3f(rgba.getRed() / 255.0F, rgba.getGreen() / 255.0F, rgba.getBlue() / 255.0F);
//                }
//
//                GL11.glScalef(1.01F, 1.01F, 1.01F);
//                GL11.glTranslatef(0.003F, 0.00F, 0.0F);
//                this.myModel.armRight2.showModel = true;
//                this.myModel.armRight2.render(0.0625F);
//                this.myModel.armRight2.showModel = prevArmR2Visible;
//                GL11.glColor3f(1.0F, 1.0F, 1.0F);
//                GL11.glPopMatrix();
//            } else {
//                boolean prevArmR2Visible = this.myModel.armRight2.showModel;
//
//                this.bindTexture(this.getEntityTexture(player));
//                this.myModel.bipedRightArm.render(0.0625F);
//                GL11.glPushMatrix();
//                GL11.glScalef(1.05F, 1.05F, 1.05F);
//                GL11.glTranslatef(0.015F, 0.00F, 0.0F);
//                this.myModel.armRight2.showModel = true;
//                this.myModel.armRight2.render(0.0625F);
//                this.myModel.armRight2.showModel = prevArmR2Visible;
//                GL11.glPopMatrix();
//            }
//        }
//    }

//    private ResourceLocation tryLoadArmorPiece(String part, EntityPlayer player, ItemStack stack, int pass) {
//        ResourceLocation resLoc;
//        String unlocName = stack.getUnlocalizedName().replace(':', '_');
//        String domain = Utilities.getModName(stack.getItem());
//        if( this.unknownTextureColorMap.containsKey(unlocName) ) {
//            resLoc = new ResourceLocation("sanplayermodel", "textures/entity/player/SanPlayer_" + part + "_unknown.png");
//        } else {
//            resLoc = new ResourceLocation("sanplayermodel", "textures/entity/player/" + domain + "/SanPlayer_" + part + '_' + unlocName + ".png");
//            try {
//                Minecraft.getMinecraft().getResourceManager().getResource(resLoc);
//            } catch( IOException ex ) {
//                SanPlayerModel.LOG.printf(Level.WARN, "Can't load armor texture for item %s from domain %s!", unlocName, domain);
//                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.format("Can't load armor texture for item %s from domain %s!", unlocName, domain)));
//                resLoc = RenderBiped.getArmorResource(player, stack, pass, null);
//                try( InputStream textureStream = Minecraft.getMinecraft().getResourceManager().getResource(resLoc).getInputStream() ) {
//                    this.unknownTextureColorMap.put(unlocName, AverageColorHelper.getAverageColor(textureStream));
//                } catch( IOException ex2 ) {
//                    SanPlayerModel.LOG.printf(Level.WARN, "Can't get avg. color for armor texture %s!", unlocName);
//                    SanPlayerModel.LOG.log(Level.WARN, "", ex2);
//                    this.unknownTextureColorMap.put(unlocName, new ColorObject(255, 255, 255, 255));
//                }
//
//                resLoc = new ResourceLocation("sanplayermodel", "textures/entity/player/SanPlayer_" + part + "_unknown.png");
//            }
//        }
//
//        return resLoc;
//    }
//
    @Override
    public void onResourceManagerReload(IResourceManager resManager) {
//        this.unknownTextureColorMap.clear();
//        this.hatRenderList.clear();
    }
//
//    public static class CubeLoader
//    {
//        public CubeLoaderCube[] cubes = new CubeLoaderCube[0];
//        public boolean hideTails;
//        private ModelRenderer[] cubeInsts = new ModelRenderer[1];
//
//        public static CubeLoader loadFromResource(String unlocName) {
//            try( BufferedReader in = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager()
//                                                                                       .getResource(new ResourceLocation("sanplayermodel", unlocName + ".json"))
//                                                                                       .getInputStream())) )
//            {
//                return new Gson().fromJson(in, CubeLoader.class);
//            } catch( IOException ex ) {
//                SanPlayerModel.LOG.printf(Level.WARN, "Can't load model for item %s!", unlocName);
////                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.format("Can't load model for item %s!", unlocName)));
//                return null;
//            }
//        }
//
//        public void initCubeInstances(ModelBase model) {
//            this.cubeInsts = new ModelRenderer[this.cubes.length];
//            ModelRenderer parent = null;
//            for( int index = 0; index < this.cubes.length; index++ ) {
//                CubeLoaderCube cubeDef = this.cubes[index];
//                this.cubeInsts[index] = ModelBoxBuilder.newBuilder(model).setTexture(cubeDef.textureX, cubeDef.textureY, cubeDef.mirror)
//                                                       .setLocation(cubeDef.rotationPointX, cubeDef.rotationPointY, cubeDef.rotationPointZ)
//                                                       .setRotation(cubeDef.rotationX, cubeDef.rotationY, cubeDef.rotationZ)
//                                                       .getBox(cubeDef.boxX, cubeDef.boxY, cubeDef.boxZ, cubeDef.sizeX, cubeDef.sizeY,
//                                                               cubeDef.sizeZ, cubeDef.scale);
//                if( index == 0 ) {
//                    parent = this.cubeInsts[index];
//                } else {
//                    parent.addChild(this.cubeInsts[index]);
//                }
//            }
//        }
//
//        public ModelRenderer getCubeParent() {
//            return this.cubeInsts.length > 0 ? this.cubeInsts[0] : null;
//        }
//    }
//
//    public static class CubeLoaderCube
//    {
//        public int textureX;
//        public int textureY;
//        public boolean mirror;
//        public float boxX;
//        public float boxY;
//        public float boxZ;
//        public int sizeX;
//        public int sizeY;
//        public int sizeZ;
//        public float rotationPointX;
//        public float rotationPointY;
//        public float rotationPointZ;
//        public float rotationX;
//        public float rotationY;
//        public float rotationZ;
//        public float scale;
//    }
}
