package org.agoncal.fascicle.quarkus.reactive.mutiny;

import io.smallrye.mutiny.Multi;

public class GettingStarted {
  public static void main(String[] args) {
    Multi.createFrom().items("hello", "world")
      .onItem().transform(s -> s.toUpperCase() + " ")
      .onCompletion().continueWith("!")
      .subscribe().with(System.out::print);
  }
}
