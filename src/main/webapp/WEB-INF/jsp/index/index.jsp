<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="jquery" value="${pageContext.servletContext.contextPath}/jquery" />
<c:set var="jqueryUI" value="${jquery}/jquery-ui-1.11.2.custom/" />
<c:if test="${empty aba}">
	<c:set var="aba" value="0" />	
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap 101 Template</title>
	<link rel="stylesheet" href="${jqueryUI}/jquery-ui.min.css">
	<link rel="stylesheet" href="${jqueryUI}/jquery-ui.theme.min.css">
	
  	<script src="${jquery}/jquery-1.11.1.min.js"></script>
  	<script src="${jqueryUI}/jquery-ui.min.js"></script>
	
	<script>
		$(function() {
			$( "#accordion" ).accordion(
				{ active: <c:out value="${aba}" />,
					collapsible: true
				}
			);
	  	});
	</script>
	<style>
		.numero {
			text-align:center;
		}
	</style>
</head>
<body>
<h1>AMIL TESTE</h1>
	<div id="accordion">
		<h3>Upload LOG</h3>
		<div>
			<form class="form-horizontal" method="post" enctype="multipart/form-data" action="<c:url value='/upload' />">
				
				<fieldset>
			      <input type="file" name="arquivo" id="arquivo"class="ui-widget-content ui-corner-all" />
			      <input type="submit" />
			    </fieldset>
				
			</form>
		</div>
		<h3>Ranking Partida ${numPartida}</h3>
		<div>
			<table border="1" style="display:${aba == 0 ? 'none' : 'block'}">
				<tr>
					<th>NOME</th>
					<th>MATOU</th>
					<th>MORREU</th>
					<th>ARMA PREFERIDA</th>
					<th>MATOU EM SEQUENCIA</th>
					<th>VENCEU SEM MORRER</th>
					<th>MATOU 5 EM 1 MINUTO</th>
				</tr>
				<c:forEach var="rankingTO" items="${listRankingTO}">
					<tr>
						<td>${rankingTO.jogador.nome}</td>
						<td class="numero">${rankingTO.qtdAssassinatos}</td>
						<td class="numero">${rankingTO.qtdMortes}</td>
						<td>
							<c:forEach var="arma" items="${rankingTO.armasPreferidas}">
								${arma.nome} - usou x${rankingTO.qtdArmaPreferida} <br />
							</c:forEach>
						</td>
						<td class="numero">${rankingTO.qtdAssassinatosSequencia}</td>
						<td>${rankingTO.venceuSemMorrer == true ? "SIM" : "NÃO"}</td>
						<td>${rankingTO.matou5Em1Minuto == true ? "SIM" : "NÃO"}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<h3>Outras Partidas</h3>
		<div>
			<c:forEach var="numPartidas" items="${numPartidas}">
				<a href="<c:url value='/lista/${numPartidas}' />">${numPartidas}</a>&nbsp;&nbsp;&nbsp; 
			</c:forEach>
		</div>
	</div>
			
</body>