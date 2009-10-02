package ch.ivyteam.ivy.example.process.element.extensions;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JLabel;

import ch.ivyteam.awtExt.AWTUtil;
import ch.ivyteam.ivy.persistence.PersistencyException;
import ch.ivyteam.ivy.process.engine.IRequestId;
import ch.ivyteam.ivy.process.extension.IIvyScriptEditor;
import ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEnvironment;
import ch.ivyteam.ivy.process.extension.UserAsynchronousProcessExtensionResult;
import ch.ivyteam.ivy.process.extension.impl.AbstractProcessExtensionConfigurationEditor;
import ch.ivyteam.ivy.process.extension.impl.AbstractUserAsynchronousProcessExtension;
import ch.ivyteam.ivy.process.intermediateevent.AbstractProcessIntermediateEventBean;
import ch.ivyteam.ivy.scripting.language.IIvyScriptContext;
import ch.ivyteam.ivy.scripting.objects.CompositeObject;
import ch.ivyteam.ivy.security.SecurityManagerFactory;

/**
 * my own implementation of a call and wait bean
 * @author rwei
 * @since 30.09.2009
 */
public class MyOwnCallAndWaitBean extends AbstractUserAsynchronousProcessExtension
{
  /***/
  private static List<WaitRequest> performedRequests = new ArrayList<WaitRequest>();

  /**
   * @see ch.ivyteam.ivy.process.extension.impl.AbstractUserAsynchronousProcessExtension#performRequest(ch.ivyteam.ivy.process.engine.IRequestId, ch.ivyteam.ivy.scripting.objects.CompositeObject, ch.ivyteam.ivy.scripting.language.IIvyScriptContext)
   */
  @Override
  public UserAsynchronousProcessExtensionResult performRequest(IRequestId requestId, CompositeObject in,
          final IIvyScriptContext context) throws Exception
  {
    String eventIdentifier;
    Number time;
    Date waitUntil;
    
    time = (Number)executeIvyScript(context, getConfiguration());
    
    waitUntil = new Date(new Date().getTime()+time.longValue());
    eventIdentifier = SecurityManagerFactory.getSecurityManager().executeAsSystem(new Callable<String>(){

      public String call() throws Exception
      {
        return ""+getCase(context).getProcessModel().getIdentifier()+":"+getProcessElementIdentifier()+":"+generateGlobalUniqueEventIdentifier();
      }});
    synchronized(performedRequests)
    {
      performedRequests.add(new WaitRequest(eventIdentifier, waitUntil));
    }
    
    return new UserAsynchronousProcessExtensionResult(in, eventIdentifier);
  }
  
  /**
   *  An editor that is called from the PI-inscription mask used to
   *  set configuration parameters for the PI-Bean. Provides the
   *  configuration as string
   *
   *@author     Peter Koch
   *@created    19. November 2001
   */
  @SuppressWarnings("serial")
  public static class Editor extends AbstractProcessExtensionConfigurationEditor
  {
    /** */
    private IIvyScriptEditor mainTxt;


    /**
     * Creates the editor panel content<br>
     * Clients are expected to override this method. 
     * @param editorPanel the editor panel 
     * @param editorEnvironment the editor environment
     */
    @Override
    protected void createEditorPanelContent(Container editorPanel, IProcessExtensionConfigurationEditorEnvironment editorEnvironment)
    {
      mainTxt = editorEnvironment.createIvyScriptEditor();
      JLabel label = new JLabel(
          "Time to wait in milliseconds (number or attribute): ");
      AWTUtil.constrain(editorPanel, label, 0,0,1,1,GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0,0.0,0,0,0,0);
      AWTUtil.constrain(editorPanel, mainTxt.getComponent(), 0,1,1,1,GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 1.0,0.0,0,0,0,0);

    }

    /**
     * Load the data of the configuration to the ui widgets.<br>
     * Clients are expected to override this method. 
     */
    @Override
    protected void loadUiDataFromConfiguration()
    {
      mainTxt.setText(getConfiguration());
    }
    
    /**
     * Save the data in the ui widgets to the configuration<br>
     * Clients are expected to override this method. 
     * @return true if save was successful, otherwise false
     */
    @Override
    protected boolean saveUiDataToConfiguration()
    {
      setBeanConfiguration(mainTxt.getText());
      return true;
    }
  }
  
  /**
   * The intermediate event part of this call and wait bean
   * @author rwei
   * @since 21.08.2009
   */
  public static class IntermediateEvent extends AbstractProcessIntermediateEventBean
  {
    /** The prefix that must be fullfit by event identifier in order to belong to this bean */ 
    private String eventIdPrefix;
    
    /**
     * Constructor
     */
    public IntermediateEvent()
    {
      super("Wait", "Waits a certain time", java.util.Date.class);
    }
    
    /**
     * @see ch.ivyteam.ivy.process.intermediateevent.AbstractProcessIntermediateEventBean#poll()
     */
    @Override
    public void poll()
    {
      List<WaitRequest> toRemove = new ArrayList<WaitRequest>();
      synchronized(performedRequests)
      {
        for (WaitRequest request: performedRequests)
        {
          try
          {
            if ((request.getEventIdentifier().startsWith(getEventIdPrefix()))&&
                (request.getWaitUntil().before(new Date())))
            {          
              try
              {
                getEventBeanRuntime().fireProcessIntermediateEventEx(
                      request.getEventIdentifier(), request.getWaitUntil(), "Hello");
              }
              finally
              {
                toRemove.add(request);
              }
            }
          }
          catch(PersistencyException ex)
          {
            getEventBeanRuntime().getRuntimeLogLogger().error(ex);
          }
  
        }
        for (WaitRequest request: toRemove)
        {
          performedRequests.remove(request);
        }
      }
    }

    /**
     * Gets the event id prefix of this call and wait process element
     * @return event id prefix
     * @throws PersistencyException 
     */
    private String getEventIdPrefix() throws PersistencyException
    {
      if (eventIdPrefix == null)
      {
        eventIdPrefix = getEventBeanRuntime().getIntermediateEventElement().getProcessModelVersion().getIdentifier()+":"+
                            getEventBeanRuntime().getIntermediateEventElement().getProcessElementId();
      }
      return eventIdPrefix;
    }
    
  }
  
  /**
   * The Wait request holds information how long to wait
   * @author rwei
   * @since 11.08.2009
   */
  private static class WaitRequest
  {
    /** The event identifier */
    private String fEventIdentifier;
    
    /** The time until to wait */
    private Date fWaitUntil;

    /**
     * Constructor
     * @param eventIdentifier
     * @param waitUntil
     */
    WaitRequest(String eventIdentifier, Date waitUntil)
    {
      super();
      this.fEventIdentifier = eventIdentifier;
      this.fWaitUntil = waitUntil;
    }

    /**
     * Returns the EventIdentifier
     * @return the EventIdentifier
     */
    String getEventIdentifier()
    {
      return fEventIdentifier;
    }

    /**
     * Returns the WaitUntil
     * @return the WaitUntil
     */
    Date getWaitUntil()
    {
      return fWaitUntil;
    }
  }}
