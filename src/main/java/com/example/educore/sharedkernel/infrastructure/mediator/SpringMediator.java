package com.example.educore.sharedkernel.infrastructure.mediator;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.sharedkernel.application.Mediator;
import com.example.educore.sharedkernel.application.Query;
import com.example.educore.sharedkernel.application.QueryHandler;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpringMediator implements Mediator {

    private final Map<Class<?>, CommandHandler<?, ?>> commandHandlers = new HashMap<>();
    private final Map<Class<?>, QueryHandler<?, ?>> queryHandlers = new HashMap<>();

    public SpringMediator(List<CommandHandler<?, ?>> commandHandlers, List<QueryHandler<?, ?>> queryHandlers) {
        commandHandlers.forEach(h -> register(h, CommandHandler.class, this.commandHandlers));
        queryHandlers.forEach(h -> register(h, QueryHandler.class, this.queryHandlers));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <R> R send(Command<R> command) {
        CommandHandler handler = commandHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No CommandHandler registered for " + command.getClass().getName());
        }
        return (R) handler.handle(command);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <R> R ask(Query<R> query) {
        QueryHandler handler = queryHandlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException("No QueryHandler registered for " + query.getClass().getName());
        }
        return (R) handler.handle(query);
    }

    private static <H> void register(H handler, Class<?> handlerInterface, Map<Class<?>, H> registry) {
        Class<?> messageType = ResolvableType.forClass(handler.getClass())
                .as(handlerInterface)
                .getGeneric(0)
                .resolve();

        if (messageType == null) {
            throw new IllegalStateException(
                    "Cannot resolve message type for handler " + handler.getClass().getName());
        }

        H previous = registry.putIfAbsent(messageType, handler);
        if (previous != null) {
            throw new IllegalStateException(
                    "Duplicate handler for " + messageType.getName() + ": "
                            + previous.getClass().getName() + " and " + handler.getClass().getName());
        }
    }
}
