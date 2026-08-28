package net.mcreator.createmixandclean.gas.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.mcreator.createmixandclean.gas.GasCellAccess;
import net.mcreator.createmixandclean.gas.GasCellFactory;
import net.mcreator.createmixandclean.gas.GasType;
import net.mcreator.createmixandclean.gas.pocket.GasPocket;
import net.mcreator.createmixandclean.gas.pocket.GasPocketManager;
import net.mcreator.createmixandclean.gas.tick.GasTickScheduler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Map;

@EventBusSubscriber(modid = "create_mix_and_clean")
public class GasDebugCommands {

    @SubscribeEvent
    public static void onRegister(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("createmac")
                .then(Commands.literal("vol")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(GasDebugCommands::runVol)))
                .then(Commands.literal("pocket")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(GasDebugCommands::runPocket)))
                .then(Commands.literal("fill")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .then(Commands.argument("gas", StringArgumentType.word())
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0, 15))
                                                .executes(GasDebugCommands::runFill)))))
                .then(Commands.literal("wake")
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(GasDebugCommands::runWake))));
    }

    private static int runVol(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        if (!(source.getLevel() instanceof ServerLevel level)) return 0;
        BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");

        Map<GasType, Float> composition = GasPocketManager.get(level).getComposition(level, pos);
        if (composition.isEmpty()) {
            source.sendSuccess(() -> Component.literal("No gas pocket data at " + pos.toShortString()), false);
            return 1;
        }
        composition.forEach((gas, frac) ->
                source.sendSuccess(() -> Component.literal(
                        gas.name() + ": " + String.format("%.1f", frac * 100) + "% VOL"), false));
        return 1;
    }

    private static int runPocket(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        if (!(source.getLevel() instanceof ServerLevel level)) return 0;
        BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");

        GasPocket pocket = GasPocketManager.get(level).getOrBuildPocket(level, pos);
        source.sendSuccess(() -> Component.literal(
                "cells=" + pocket.cellCount()
                        + " capped=" + pocket.capped
                        + " skyExposed=" + pocket.skyExposed
                        + " settled=" + pocket.settled
                        + " stableStreak=" + pocket.stableStreak), false);
        return 1;
    }

    private static int runFill(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        if (!(source.getLevel() instanceof ServerLevel level)) return 0;
        BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
        String gasName = StringArgumentType.getString(ctx, "gas");
        float amount = FloatArgumentType.getFloat(ctx, "amount");

        GasType type = GasType.byName(gasName);
        if (type == null) {
            source.sendFailure(Component.literal("Unknown gas: " + gasName));
            return 0;
        }

        GasCellAccess access = GasCellFactory.at(level, pos);
        if (access == null) {
            source.sendFailure(Component.literal("Cannot place gas at " + pos.toShortString()));
            return 0;
        }
        access.setConcentration(type, amount);
        GasTickScheduler.wake(level, pos);
        source.sendSuccess(() -> Component.literal(
                "Set " + type.name() + " to " + amount + " at " + pos.toShortString()), false);
        return 1;
    }

    private static int runWake(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        if (!(source.getLevel() instanceof ServerLevel level)) return 0;
        BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
        GasTickScheduler.wake(level, pos);
        source.sendSuccess(() -> Component.literal("Woke cell at " + pos.toShortString()), false);
        return 1;
    }
}
