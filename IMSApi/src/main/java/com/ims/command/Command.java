package com.ims.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
 public Object doExecute(HttpServletRequest request, HttpServletResponse response);
}
