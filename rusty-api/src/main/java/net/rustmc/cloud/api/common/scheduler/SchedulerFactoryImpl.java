package net.rustmc.cloud.api.common.scheduler;

import lombok.Getter;
import net.rustmc.cloud.api.scheduler.IScheduler;
import net.rustmc.cloud.api.scheduler.ISchedulerFactory;

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 25.10.2022, Di.
 */
public class SchedulerFactoryImpl implements ISchedulerFactory {

    @Getter
    private static final ISchedulerFactory defaultFactory = new SchedulerFactoryImpl();

    private final LinkedList<IScheduler> registry = new LinkedList<>();

    @Override
    public IScheduler of(int identifier) {
        return registry.get(identifier);
    }

    @Override
    public IScheduler register() {
        final var out = new DefaultSchedulerImpl(this.registry.size());
        registry.addLast(out);
        return out;
    }

    @Override
    public IScheduler register(int delay, int period, Consumer<IScheduler> onTick) {
        return null;
    }

    @Override
    public IScheduler register(int delay, Consumer<IScheduler> onComplete) {
        return null;
    }
}
