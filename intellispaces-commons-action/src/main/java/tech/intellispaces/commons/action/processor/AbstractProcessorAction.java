package tech.intellispaces.commons.action.processor;

import tech.intellispaces.commons.action.AbstractAction;
import tech.intellispaces.commons.action.Action;
import tech.intellispaces.commons.action.Actions;
import tech.intellispaces.commons.action.chain.processor.ChainProcessorActions;
import tech.intellispaces.commons.action.runnable.RunnableAction;
import tech.intellispaces.commons.action.wrapper.WrapperAction;

import java.util.function.Function;

public abstract class AbstractProcessorAction extends AbstractAction implements ProcessorAction {

  @Override
  public Object execute(Object[] inputs) {
    process(inputs);
    return null;
  }

  @Override
  public int executeReturnInt(Object[] inputs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double executeReturnDouble(Object[] inputs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ProcessorAction andThen(Action nextAction) {
    return new ProcessorActionBasedOnGeneralAction(ChainProcessorActions.get(this, nextAction));
  }

  @Override
  public RunnableAction asRunnable(Object[] inputs) {
    return Actions.runnableAction(this::process, inputs);
  }

  @Override
  public <W extends WrapperAction> ProcessorAction wrapProcessorAction(
      Function<Action, W> wrapperFactory
  ) {
    return new ProcessorActionBasedOnGeneralAction(wrapperFactory.apply(this));
  }
}
