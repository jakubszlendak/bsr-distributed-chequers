#include "fullcommand.h"



QString fullCommand::par1()
{
    if(parameters.length()>0)
    {
        return parameters[0];
    }
    else
    {
        return "";
    }
}
QString fullCommand::par2()
{
    if(parameters.length()>1)
    {
        return parameters[1];
    }
    else
    {
        return "";
    }
}
QString fullCommand::par3()
{
    if(parameters.length()>2)
    {
        return parameters[2];
    }
    else
    {
        return "";
    }
}
QString fullCommand::par4()
{
    if(parameters.length()>3)
    {
        return parameters[3];
    }
    else
    {
        return "";
    }
}

fullCommand::fullCommand()
{

}
