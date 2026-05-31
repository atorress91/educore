package com.example.educore.sharedkernel.infrastructure.mediator;

import com.example.educore.sharedkernel.application.Command;
import com.example.educore.sharedkernel.application.CommandHandler;
import com.example.educore.sharedkernel.application.Query;
import com.example.educore.sharedkernel.application.QueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SpringMediatorTest {

    record GreetCommand(String name) implements Command<String> {}
    record CountQuery(int n) implements Query<Integer> {}
    record UnregisteredCommand() implements Command<Void> {}

    static class GreetHandler implements CommandHandler<GreetCommand, String> {
        @Override public String handle(GreetCommand c) { return "Hello, " + c.name(); }
    }

    static class CountHandler implements QueryHandler<CountQuery, Integer> {
        @Override public Integer handle(CountQuery q) { return q.n() * 2; }
    }

    @Test
    void dispatches_command_to_registered_handler() {
        SpringMediator mediator = new SpringMediator(List.of(new GreetHandler()), List.of());

        String result = mediator.send(new GreetCommand("Ada"));

        assertThat(result).isEqualTo("Hello, Ada");
    }

    @Test
    void dispatches_query_to_registered_handler() {
        SpringMediator mediator = new SpringMediator(List.of(), List.of(new CountHandler()));

        Integer result = mediator.ask(new CountQuery(21));

        assertThat(result).isEqualTo(42);
    }

    @Test
    void throws_when_no_command_handler_registered() {
        SpringMediator mediator = new SpringMediator(List.of(), List.of());

        assertThatThrownBy(() -> mediator.send(new UnregisteredCommand()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No CommandHandler");
    }

    @Test
    void rejects_duplicate_handlers_for_same_command() {
        assertThatThrownBy(() ->
                new SpringMediator(List.of(new GreetHandler(), new GreetHandler()), List.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Duplicate handler");
    }
}
