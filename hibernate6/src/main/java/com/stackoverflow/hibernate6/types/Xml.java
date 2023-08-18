package com.stackoverflow.hibernate6.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Xml implements Serializable {

    private final String xml;

}
