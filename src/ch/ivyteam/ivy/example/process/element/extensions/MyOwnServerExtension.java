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
  @Override
  public String getIdentifier()
  {
    return getClass().getName();
  }

  /**
   * @see ch.ivyteam.ivy.lifecycle.ILifecycle#getName()
   */
  @Override
  public String getName()
  {
    return "MyOwn";
  }

  /**
   * @see ch.ivyteam.ivy.lifecycle.ILifecycle#start(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void start(IProgressMonitor monitor) throws Exception
  {
  }

  /**
   * @see ch.ivyteam.ivy.lifecycle.ILifecycle#stop(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void stop(IProgressMonitor monitor) throws Exception
  {
  }

  public String[] getMethods()
  {
    return new String[]{"a", "b", "c"};
  }
}
