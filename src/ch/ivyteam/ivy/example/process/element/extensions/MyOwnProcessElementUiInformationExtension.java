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

  public MyOwnProcessElementUiInformationExtension()
  {
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension#getDescription(java.lang.String, java.util.Locale)
   */
  @Override
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
    else if (processElementClassName.equals("MyOwnProgramUserInterface"))
    {
      return "My Own Program User Interface can be used to show a user interface to the user during a process";
    }
    else if (processElementClassName.equals("MyOwnCallAndWait"))
    {
      return "My Own Call&Wait can be used to call My Own functions and the wait for the response";
    }
    return null;
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension#getName(java.lang.String, java.util.Locale)
   */
  @Override
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
    else if (processElementClassName.equals("MyOwnProgramUserInterface"))
    {
      return "My Own User Dialog";
    }
    else if (processElementClassName.equals("MyOwnCallAndWait"))
    {
      return "My Own Call&Wait";
    }
    return null;
  }

  /**
   * @see ch.ivyteam.ivy.designer.process.ui.info.IProcessElementUiInformationExtension#getShortName(java.lang.String, java.util.Locale)
   */
  @Override
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
    else if (processElementClassName.equals("MyOwnCallAndWait"))
    {
      return "MyOwnCall&Wait";
    }
    return null;
  }

}
