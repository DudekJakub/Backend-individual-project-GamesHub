package com.gameshub.service;

import com.gameshub.subscribe.SubscribeEvent;

public interface GameObservable {

    void notifyObserver(final SubscribeEvent subscribeEvent);
}
