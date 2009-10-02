package ch.ivyteam.ivy.example.process.element.extensions;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import ch.ivyteam.ivy.process.element.IBpmnIconFactory;
import ch.ivyteam.ivy.process.element.IExtensibleProcessElementFactory;
import ch.ivyteam.ivy.process.element.IExtensibleStandardProcessElementExtension;

/**
 * Xpert.ivy extensible standard process element extension for my own process elements
 * @author rwei
 * @since 19.08.2008
 */
public class MyOwnExtensibleStandardProcessElementExtension implements
        IExtensibleStandardProcessElementExtension
{

  /**
   * @see ch.ivyteam.ivy.components.IStandardProcessElementExtension#getName()
   */
  public String getName()
  {
    return "MyOwnProcessElements";
  }

  /**
   * @see ch.ivyteam.ivy.components.IStandardProcessElementExtension#createBpmnIcon(ch.ivyteam.ivy.process.element.IBpmnIconFactory, java.lang.String)
   */
  public Icon createBpmnIcon(IBpmnIconFactory iconFactory, String iconName)
  {
    URL iconUrl;
    if (iconName.equals("MyOwnStepIcon"))
    {
      iconUrl = getClass().getClassLoader().getResource("/ch/ivyteam/ivy/example/process/element/extensions/MyOwnElement.png");
      if (iconUrl != null)
      {     
        return iconFactory.createActivityBpmnIcon(new ImageIcon(iconUrl), false);
      }
    }
    else if (iconName.equals("MyOwnStartEventIcon"))
    {
      iconUrl = getClass().getClassLoader().getResource("/ch/ivyteam/ivy/example/process/element/extensions/MyOwnElement_small.png");
      if (iconUrl != null)
      {     
        return iconFactory.createStartEventBpmnIcon(new ImageIcon(iconUrl));
      }
    }
    else if (iconName.equals("MyOwnIntermediateEventIcon"))
    {
      iconUrl = getClass().getClassLoader().getResource("/ch/ivyteam/ivy/example/process/element/extensions/MyOwnElement_small.png");
      if (iconUrl != null)
      {     
        return iconFactory.createIntermediateEventBpmnIcon(new ImageIcon(iconUrl));
      }
    }
    else if (iconName.equals("MyOwnProgramUserInterface"))
    {
      iconUrl = getClass().getClassLoader().getResource("/ch/ivyteam/ivy/example/process/element/extensions/MyOwnElement_small.png");
      if (iconUrl != null)
      {     
        return iconFactory.createActivityBpmnIcon(new ImageIcon(iconUrl), false);
      }
    }
    else if (iconName.equals("MyOwnCallAndWaitIcon"))
    {
      iconUrl = getClass().getClassLoader().getResource("/ch/ivyteam/ivy/example/process/element/extensions/MyOwnElement_small.png");
      if (iconUrl != null)
      {     
        return iconFactory.createIntermediateEventBpmnIcon(new ImageIcon(iconUrl));
      }
    }
    return null;
  }

  /**
   * @see ch.ivyteam.ivy.process.element.IExtensibleStandardProcessElementExtension#declareProcessElements(ch.ivyteam.ivy.process.element.IExtensibleProcessElementFactory)
   */
  public void declareProcessElements(IExtensibleProcessElementFactory factory)
  {
    factory.declareProgramInterfaceProcessElement("MyOwnStep", "ch.ivyteam.ivy.example.process.element.extensions.MyOwnPiBean", "MyOwnStepIcon");
    factory.declareStartEventProcessElement("MyOwnStartEvent", "ch.ivyteam.ivy.example.process.element.extensions.MyOwnStartEventBean", "MyOwnStartEventIcon");
    factory.declareIntermediateEventProcessElement("MyOwnIntermediateEvent", "ch.ivyteam.ivy.example.process.element.extensions.MyOwnIntermediateEventBean", "MyOwnIntermediateEventIcon");
    factory.declareProgramUserInterfaceProcessElement("MyOwnProgramUserInterface", "ch.ivyteam.ivy.example.process.element.extensions.MyOwnProgramUserInterfaceBean", "MyOwnProgramUserInterface");
    factory.declareCallAndWaitProcessElement("MyOwnCallAndWait", "ch.ivyteam.ivy.example.process.element.extensions.MyOwnCallAndWaitBean", "MyOwnCallAndWaitIcon");
  }
}
