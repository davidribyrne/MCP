package mcp.options;
// package org.reconmaster.options;
//
// import org.reconmaster.commons.general.BooleanUtils;
//
// public class ReconOption
// {
// private final String longOption;
// private final String description;
// private final boolean argument;
// private final String defaultValue;
// private final String argumentName;
// private final Character shortOption;
//
// public ReconOption(Character shortOption, String longOption, String description, boolean argument)
// {
// this(shortOption, longOption, description, argument, null);
// }
//
// public ReconOption(Character shortOption, String longOption, String description, boolean argument, String
// defaultValue)
// {
// this(shortOption, longOption, description, argument, defaultValue, null);
// }
//
//
// public ReconOption(Character shortOption, String longOption, String description, boolean argument, String
// defaultValue, String argumentName)
// {
// this.longOption = longOption;
// this.description = description;
// this.argument = argument;
// this.defaultValue = defaultValue;
// this.argumentName = argumentName;
// this.shortOption = shortOption;
// }
//
// public String[] getValues()
// {
// return ReconOptions.getInstance().getValues(this);
// }
//
// public String getValue()
// {
// return ReconOptions.getInstance().getValue(this);
// }
//
//
// public boolean isPresent()
// {
// return ReconOptions.getInstance().isPresent(this);
// }
//
// public boolean isEnabled()
// {
// if (hasArgument())
// return getValue() == null ? false : BooleanUtils.friendlyParse(getValue());
// else
// return isPresent();
// }
//
// public String getDescription()
// {
// return description;
// }
//
// public boolean hasArgument()
// {
// return argument;
// }
//
// public String getDefaultValue()
// {
// return defaultValue;
// }
//
// public String getArgumentName()
// {
// return argumentName;
// }
//
// public String getLongOption()
// {
// return longOption;
// }
//
// public Character getShortOption()
// {
// return shortOption;
// }
//
//
// }
