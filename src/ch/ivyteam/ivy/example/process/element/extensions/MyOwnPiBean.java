package ch.ivyteam.ivy.example.process.element.extensions;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.ivyteam.awtExt.AWTUtil;
import ch.ivyteam.awtExt.CommonDialogs;
import ch.ivyteam.ivy.process.engine.IRequestId;
import ch.ivyteam.ivy.process.extension.IIvyScriptEditor;
import ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEnvironment;
import ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEx;
import ch.ivyteam.ivy.process.extension.impl.AbstractUserProcessExtension;
import ch.ivyteam.ivy.scripting.language.IIvyScriptContext;
import ch.ivyteam.ivy.scripting.objects.CompositeObject;

/**
 * Implementation of my own pi bean
 * @author rwei
 * @since 26.08.2008
 */
public class MyOwnPiBean extends AbstractUserProcessExtension
{
  
  /**
   * @see ch.ivyteam.ivy.process.extension.IUserProcessExtension#perform(ch.ivyteam.ivy.process.engine.IRequestId, ch.ivyteam.ivy.scripting.objects.CompositeObject, ch.ivyteam.ivy.scripting.language.IIvyScriptContext)
   */
  @Override
  public CompositeObject perform(IRequestId requestId, CompositeObject in,
          IIvyScriptContext context) throws Exception
  {
    IIvyScriptContext ownContext;
    CompositeObject out;
    
    out = in.clone();
    
    ownContext = createOwnContext(context);
    declareAndInitializeVariable(ownContext, "myOwn", MyOwnServerExtension.class.getName(), getServerExtension(context, MyOwnServerExtension.class.getName()));
    declareAndInitializeVariable(ownContext, "out", out.getClass().getName(), out);

    executeIvyScript(ownContext, getConfiguration());
    return out;
  }

  public static class Editor extends JPanel implements IProcessExtensionConfigurationEditorEx, ActionListener
  {
    /** editor */
    private IIvyScriptEditor editor;
    
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
    @Override
    public void setConfiguration(String config)
    {
      editor.setText(config);
    }

    /**
     *  Gets the component attribute of the Editor object
     *
     *@return    this
     */
    @Override
    public Component getComponent()
    {
      return this;
    }


    /**
     *  Gets the configuration
     *
     *@return    The configuration as an String
     */
    @Override
    public String getConfiguration()
    {
      try
      {
        return (editor.getText());
      }
      catch (Exception ex)
      {
        return "";
      }
    }


    /**
     *@return boolean
     */
    @Override
    public boolean acceptInput()
    {
      return true;
    }

    /**
     * @see ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEx#setEnvironment(ch.ivyteam.ivy.process.extension.IProcessExtensionConfigurationEditorEnvironment)
     */
    @Override
    public void setEnvironment(
            IProcessExtensionConfigurationEditorEnvironment environment)
    {
      env = environment;
      editor = env.createIvyScriptEditor(Arrays.asList(new IProcessExtensionConfigurationEditorEnvironment.Variable[]{
              new IProcessExtensionConfigurationEditorEnvironment.Variable("myOwn", "ch.ivyteam.ivy.example.process.element.extensions.MyOwnServerExtension"),
              new IProcessExtensionConfigurationEditorEnvironment.Variable("out", null),
              }), null);
      JLabel label = new JLabel(
      "Method to call: ");
      JButton button = new JButton("Browse ...");
      button.addActionListener(this);
      AWTUtil.constrain(this, label, 0,0,1,1,GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0,0.0,0,0,0,0);
      AWTUtil.constrain(this, editor.getComponent(), 0,1,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST, 1.0,1.0,0,0,0,0);
      AWTUtil.constrain(this, button, 1,1,1,1,GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0,0.0,0,5,0,0);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      Object object;
      
      object = CommonDialogs.chooseDialog(this, "Choose Method", ((MyOwnServerExtension)env.getServerExtension(MyOwnServerExtension.class.getName())).getMethods());
      if (object != null)
      {
        editor.setText(object.toString());
      }
    }


  }
}
