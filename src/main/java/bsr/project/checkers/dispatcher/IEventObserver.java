package bsr.project.checkers.dispatcher;

public interface IEventObserver {
	
	void registerEvents();
	
	void onEvent(AbstractEvent event);
	
}
