package com.oscgc.securevideo.server.tool;

import java.util.ServiceLoader;

/**
 */
public class IdGeneratorFactory {
    
    private static class IdGeneratorFactoryHolder {
        
        static IdGenerator instance;
        
        static {
            ServiceLoader<IdGenerator> serviceLoader = ServiceLoader.load(IdGenerator.class);
            for (IdGenerator provider : serviceLoader) {
                instance = provider;
            }
        }
        
    }
    
    public static IdGenerator getInstance() {
        return IdGeneratorFactoryHolder.instance;
    }
    
    private IdGeneratorFactory() {
        
    }
}
