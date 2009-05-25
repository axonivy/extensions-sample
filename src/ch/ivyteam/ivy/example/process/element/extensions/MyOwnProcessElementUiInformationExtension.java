package ch.ivyteam.ivy.example.process.element.extensions;

import java.util.Locale;

import ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension;

/**
 * Provides the labels of my own process element
 * @author rwei
 * @since 15.09.2008
 */
public class MyOwnProcessElementUiInformationExtension implements
        IProcessElementUiInformationExtension
{

  /**
   * 
   * Constructor
   */
  public MyOwnProcessElementUiInformationExtension()
  {
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension#getDescription(java.lang.String, java.util.Locale)
   */
  public String getDescription(String processElementClassName, Locale locale)
  {
    if (processElementClassName.equals("MyOwnStep"))
    {
      return "My Own Step can be used to call My Own functions";
    }
    else if (processElementClassName.equals("MyOwnStartEvent"))
    {
      return "My Own Start Event can be used to start a process in case of a My Own event"; 
    }
    else if (processElementClassName.equals("MyOwnIntermediateEvent"))
    {
      return "My Own Intermediate Event can be used to wait for a My Own event during a process";
    }
    return null;
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension#getName(java.lang.String, java.util.Locale)
   */
  public String getName(String processElementClassName, Locale locale)
  {
    if (processElementClassName.equals("MyOwnStep"))
    {
      return "My Own Step";
    }
    else if (processElementClassName.equals("MyOwnStartEvent"))
    {
      return "My Own Start Event"; 
    }
    else if (processElementClassName.equals("MyOwnIntermediateEvent"))
    {
      return "My Own Intermediate Event";
    }
    return null;
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension#getShortName(java.lang.String, java.util.Locale)
   */
  public String getShortName(String processElementClassName, Locale locale)
  {
    if (processElementClassName.equals("MyOwnStep"))
    {
      return "MyOwnStep";
    }
    else if (processElementClassName.equals("MyOwnStartEvent"))
    {
      return "MyOwnEvent"; 
    }
    else if (processElementClassName.equals("MyOwnIntermediateEvent"))
    {
      return "MyOwnIntermediate";
    }
    return null;
  }

}
