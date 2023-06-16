package com.onlywar.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.onlywar.game.OnlyWar;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                return new GwtApplicationConfiguration(480,320,true);
                // Fixed size application:
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new OnlyWar();
        }
}