package com.livesports.model;

import com.livesports.visitor.Visitor;
public interface SportElement {
    <R> R accept(Visitor<R> visitor);
}
