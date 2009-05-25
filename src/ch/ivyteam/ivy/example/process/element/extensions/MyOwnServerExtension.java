package ch.ivyteam.ivy.example.process.element.extensions;

import org.eclipse.core.runtime.IProgressMonitor;

import ch.ivyteam.ivy.server.IServerExtension;

/**
 * Xpert.ivy Server Extension for the 3rd party myOwn system
 * @author rwei
 * @since 19.08.2008
 */
public class MyOwnServerExtension implements IServerExtension
{
  /**
   * @see ch.ivyteam.ivy.server.IServerExtension#getIdentifier()
   */
  public String getIdentifier()
  {
    return getClass().getName();
  }

  /**
   * @see ch.ivyteam.ivy.lifecycle.ILifecycle#getName()
   */
  public String getName()
  {
    return "MyOwn";
  }

  /**
   * @see ch.ivyteam.ivy.lifecycle.ILifecycle#start(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void start(@SuppressWarnings("unused") IProgressMonitor monitor) throws Exception
  {
  }

  /**
   * @see ch.ivyteam.ivy.lifecycle.ILifecycle#stop(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void stop(@SuppressWarnings("unused") IProgressMonitor monitor) throws Exception
  {
  }

  /**
   * @return
   */
  public String[] getMethods()
  {
    return new String[]{"a", "b", "c"};
  }
}
