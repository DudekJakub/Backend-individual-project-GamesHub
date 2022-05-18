package com.gameshub.service;

import com.gameshub.subscribe.SubscribeEvent;

public interface IGameObservable {

    void notifyObserver(final SubscribeEvent subscribeEvent);
}
