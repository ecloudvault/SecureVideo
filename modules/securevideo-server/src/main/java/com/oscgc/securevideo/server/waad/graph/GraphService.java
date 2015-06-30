package com.oscgc.securevideo.server.waad.graph;

import com.oscgc.securevideo.server.model.User;

import java.util.List;


public interface GraphService {

    public List<User> listUsers();

    public User getUser(String id);

}
