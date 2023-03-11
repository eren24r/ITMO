package commands;

import сlasses.Organization;

import java.util.HashSet;

public interface Command {
    boolean doo(HashSet<Organization> mySet, String s);
    String des();
    String getName();
}
