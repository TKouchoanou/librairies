package com.malo.library.command;

import java.util.ArrayList;
import java.util.List;

public interface CommandHandler<C extends Command> {

    void handle(C command, HandlingContext handlingContext);

    class HandlingContext {
        private final List<Runnable> onSuccessActions = new ArrayList<>();
        private final List<Runnable> onFailureActions = new ArrayList<>();

        public void doOnSuccess(Runnable runnable) {
            this.onSuccessActions.add(runnable);
        }

        public Iterable<Runnable> getOnSuccessActions() {
            return this.onSuccessActions;
        }

        public void doOnFailure(Runnable runnable) {
            this.onFailureActions.add(runnable);
        }

        public List<Runnable> getOnFailureActions() {
            return onFailureActions;
        }
    }
}
