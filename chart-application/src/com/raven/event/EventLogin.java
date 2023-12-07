package com.raven.event;

import com.raven.model.Model_Login;
import com.raven.model.Model_Register;

public interface EventLogin {

    public void login(Model_Login data,String name);

    public void register(Model_Register data, EventMessage message);

    public void goRegister();

    public void goLogin();

    public int attemptLogin(Model_Login loginModel);
}
