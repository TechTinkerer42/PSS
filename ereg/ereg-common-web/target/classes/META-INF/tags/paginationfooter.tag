<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@ attribute name="totalRecordCount" required="true"
	description="Number of records count."%>
<%@ attribute name="pages" required="true" type="java.lang.Integer[]"
	description="Number of pages."%>
<%@ attribute name="currentPage" required="true"
	description="Current page no."%>
<%@ attribute name="totalsPages" required="true"
	description="Total pages."%>
<%@ attribute name="hideRecordsSel" required="false"
	description="To hide drop down and Results found"%>

<div class="formRow" id="table_footer">
	<span class="rosterSpan"
		style="font-family: arial, helvetica, sans-serif; font-size: 0.91em;"><c:if test="${ hideRecordsSel  ne 'Y'}"> Results
		found:<c:out value="${totalRecordCount}" /> | </c:if>Page: ${currentPage} of
		${totalsPages} | <c:choose>
			<c:when test="${currentPage != 1}">
				<a href='javascript:firstLink(1);' >&lt;&lt;First</a>
			</c:when>
			<c:otherwise>
       &lt;&lt;First
      </c:otherwise>
		</c:choose> <c:choose>
			<c:when test="${currentPage != 1}">
				<a href='javascript:nextLink(${currentPage -1});'>&lt;Previous&nbsp;</a>
			</c:when>
			<c:otherwise>
       &lt;Previous&nbsp;
      </c:otherwise>
		</c:choose> <c:forEach items="${pages}" var="pageno">

			<c:choose>
				<c:when test="${currentPage == pageno}">
				&nbsp;${pageno}				
				</c:when>
				<c:otherwise>
					<a href="javascript:pageLink(${pageno});">&nbsp;${pageno}</a>
				</c:otherwise>
			</c:choose>



		</c:forEach> <c:choose>
			<c:when test="${currentPage < totalsPages}">
				<a href='javascript:nextLink(${ currentPage + 1});'>&nbsp;Next&gt;</a>
			</c:when>
			<c:otherwise>
      &nbsp;Next&gt;
      </c:otherwise>
		</c:choose> <c:choose>
			<c:when test="${currentPage != totalsPages}">
				<a href='javascript:nextLink(${ totalsPages});'>Last&gt;&gt;</a>
			</c:when>
			<c:otherwise>
       Last&gt;&gt;
      </c:otherwise>
		</c:choose>


	</span>
</div>