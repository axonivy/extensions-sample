package ch.ivyteam.ivy.example.process.element.extensions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;

import ch.ivyteam.awtExt.AWTUtil;
import ch.ivyteam.ivy.process.engine.IRequestId;
import ch.ivyteam.ivy.process.extension.IIvyScriptEditor;
import ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEnvironment;
import ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEx;
import ch.ivyteam.ivy.process.extension.IProgramUserInterfaceProcessExtensionListener;
import ch.ivyteam.ivy.process.extension.impl.AbstractProgramUserInterfaceProcessExtension;
import ch.ivyteam.ivy.scripting.language.IIvyScriptContext;
import ch.ivyteam.ivy.scripting.objects.CompositeObject;

/**
 * My own implementation of a program user interface
 * @author rwei
 * @since 20.05.2009
 */
public class MyOwnProgramUserInterfaceBean extends AbstractProgramUserInterfaceProcessExtension
{
  /**
   * Constructor
   */
  public MyOwnProgramUserInterfaceBean()
  {
    super(String.class);
  }
  
  /**
   * @see ch.ivyteam.ivy.process.extension.impl.AbstractProgramUserInterfaceProcessExtension#perform(ch.ivyteam.ivy.process.engine.IRequestId, ch.ivyteam.ivy.scripting.objects.CompositeObject, ch.ivyteam.ivy.scripting.language.IIvyScriptContext, ch.ivyteam.ivy.process.extension.IProgramUserInterfaceProcessExtensionListener)
   */
  @Override
  public void perform(@SuppressWarnings("unused") IRequestId requestId, CompositeObject in,
          IIvyScriptContext context,
          IProgramUserInterfaceProcessExtensionListener userInterfaceListener)
          throws Exception
  {
    new MyOwnUserInterface(
            userInterfaceListener, 
            (String)executeIvyScript(context, getConfigurationProperty("title")),
            (String)executeIvyScript(context, getConfigurationProperty("label")));
  }
  
  /**
   * @see ch.ivyteam.ivy.process.extension.impl.AbstractProgramUserInterfaceProcessExtension#getResultObjectClass()
   */
  @Override
  public Class<?> getResultObjectClass()
  {
    try
    {
      return Class.forName(getConfigurationProperty("result"));
    }
    catch(Exception ex)
    {
      return super.getResultObjectClass();
    }
  }
  
  /**
   * A simple user interface that shows a simple swing frame. Note that this is not a
   * very good sample because implementations of program user interface must display
   * the user interface on the client machine whereas this implementation shows the
   * swing frame on the server. But it illustrates in the designer how to use the program user interface.
   * @author rwei
   * @since 20.05.2009
   */
  private static class MyOwnUserInterface extends JFrame 
  {
    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** The listener to report the result of the user interface to */
    private IProgramUserInterfaceProcessExtensionListener fListener;
    
    /** The resulting text that the user inputs */
    private JTextField fInputText;
    
    
    /**
     * Constructor
     * @param listener
     * @param title
     * @param labelTxt 
     */
    public MyOwnUserInterface(IProgramUserInterfaceProcessExtensionListener listener, String title, String labelTxt) 
    {
      super(title);
      fListener = listener;
      JPanel panel = new JPanel(new BorderLayout());
      getContentPane().add(panel);
      JLabel label = new JLabel(labelTxt);
      panel.add(label, BorderLayout.NORTH);
      fInputText = new JTextField();
      panel.add(fInputText, BorderLayout.CENTER);
      JButton button = new JButton("ok");
      panel.add(button, BorderLayout.SOUTH);
      button.addActionListener(new ActionListener()
      {

        public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) 
        {
          setVisible(false);
          dispose();
          final String result = fInputText.getText();
          // do not send result to listener in the swing ui thread because this will cause an exception 
          // in the Xpert.ivy designer.
          new Thread()
          {
            @Override
            public void run() 
            {
                    fListener.userInputEnded(result);
            } 
          }.start();               
        }});
      pack();
      setVisible(true);
    }
  }
  
  /**
   * Configuration Editor
   * @author rwei
   * @since 20.05.2009
   */
  public static class Editor extends JPanel implements IProcessExtensionConfigurationEditorEx
  {
    /** editor */
    private IIvyScriptEditor editorTitle;
    
    /** editor */
    private IIvyScriptEditor editorLabel;
    
    private JTextField editorClass;
        
    /** configuration */
    private Properties configuration = new Properties();
    
    private String config;
    
    /** The environment */
    private IProcessExtensionConfigurationEditorEnvironment env;

    /**
     *  Constructor for the Editor object
     */
    public Editor()
    {
      super(new GridBagLayout());
    }

    /**
     *  Sets the configuration
     *
     *@param  config  the configuration as an String
     */
    public void setConfiguration(String _config)
    { 
      InputStream is = null;
      config = _config;
      if ((config != null) && (config.length() > 0))
      {        
        try
        {
          is = IOUtils.toInputStream(config);
          configuration.load(is);
        }
        catch(IOException ex)
        {}
        finally
        {
          IOUtils.closeQuietly(is);
        }
      }
      editorTitle.setText(configuration.getProperty("title"));
      editorLabel.setText(configuration.getProperty("label"));
      editorClass.setText(configuration.getProperty("result"));
    }

    /**
     *  Gets the component attribute of the Editor object
     *
     *@return    this
     */
    public Component getComponent()
    {
      return this;
    }


    /**
     *  Gets the configuration
     *
     *@return    The configuration as an String
     */
    public String getConfiguration()
    {
      configuration.put("title", editorTitle.getText());
      configuration.put("label", editorLabel.getText());
      configuration.put("result", editorClass.getText());
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      try
      {
        configuration.store(os, "");
        return os.toString();
      }
      catch(IOException ex)
      {
        return "";
      }
      finally
      {
        IOUtils.closeQuietly(os);
      }
    }


    /**
     *@return boolean
     */
    public boolean acceptInput()
    {
      return true;
    }

    /**
     * @see ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEx#setEnvironment(ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEnvironment)
     */
    public void setEnvironment(
            IProcessExtensionConfigurationEditorEnvironment environment)
    {
      env = environment;
      JLabel label = new JLabel("Title:");
      AWTUtil.constrain(this, label, 0,0,1,1,GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0,0.0,0,0,0,0);
      editorTitle = env.createIvyScriptEditor();
      AWTUtil.constrain(this, editorTitle.getComponent(), 0,1,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST, 1.0,1.0,0,0,0,0);
      label = new JLabel("Label:");
      AWTUtil.constrain(this, label, 0,2,1,1,GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0,0.0,0,0,0,0);
      editorLabel = env.createIvyScriptEditor();
      AWTUtil.constrain(this, editorLabel.getComponent(), 0,3,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST, 1.0,1.0,0,0,0,0);
      label = new JLabel("Result Class:");
      AWTUtil.constrain(this, label, 0,4,1,1,GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0,0.0,0,0,0,0);
      editorClass = new JTextField("");
      AWTUtil.constrain(this, editorClass, 0,5,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST, 1.0,1.0,0,0,0,0);
      editorClass.addFocusListener(new FocusListener(){

        public void focusGained(FocusEvent e)
        {
          
        }

        public void focusLost(FocusEvent e)
        {
          if (!editorClass.getText().equals(configuration.getProperty("result")))
          {
            String newConfig;
            
            newConfig = getConfiguration();
            Editor.this.firePropertyChange("configuration", config, getConfiguration());
            config = newConfig;
          }
          
        }});
    }



  }

}