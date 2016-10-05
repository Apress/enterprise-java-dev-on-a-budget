<%@ taglib uri="struts-tiles" prefix="tiles" %>

<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="style/default.css" rel="stylesheet" type="text/css">
  </head>

  <body>
    <table width="660">
      <tr>
       <td>
         <tiles:insert attribute='header'/>
       </td>
      </tr>
    </table>
    <table width="660">
      <tr>
        <td width="120" class="menu">
          <!-- menu begin -->
          <tiles:insert attribute='menu'/>
          <!-- menu end -->
        </td>
        <td width="540" class="content">
        <!-- content begin -->
          <tiles:insert attribute='body' />
        <!-- content end -->
      </tr>
    </table>
    <table width="660">
      <tr> 
        <td>
          <tiles:insert attribute='footer'/>
	    </td>
      </tr>
    </table>
  </body>
</html>