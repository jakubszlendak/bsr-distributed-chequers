package bsr.project.checkers.dispatcher;

public interface IEventObserver {
	
	void registerEvents();
	
	void onEvent(IEvent event);
	
}
