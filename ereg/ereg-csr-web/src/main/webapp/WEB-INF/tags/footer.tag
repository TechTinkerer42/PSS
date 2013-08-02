<%@tag description="The footer tag fragment for eReg CSR Web" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<footer  role="contentinfo">

				<hr />

				<div class="wrap-fcs">

					<div class="wide-fc">
						<ul>
							<li class="first"><a href="index.html">HiSET Home</a></li>
							<li><a href="states_educators/">For States and Educators</a></li>
							<li><a href="test_administrators/">For Test Center Administrators</a></li>
							<li class="last"><a href="test_takers/">For Test Takers</a></li>
						</ul>

						<ul>
							<li class="first"><a href="http://www.ets.org/about">About ETS</a></li>
							<li class="last"><a href="http://www.ets.org/legal">Legal</a></li>
						</ul>

						<ul>
							<li class="first last"><a href="http://get.adobe.com/reader">Get Adobe Reader (for PDFs)</a></li>
						</ul>
					</div>

					<div class="clearboth"></div>

					<div id="cya">
						<p>
							Copyright <span aria-hidden="true">&copy;</span>
						  <script type="text/javascript">document.write(new Date().getFullYear());</script>
							by Educational Testing Service. All rights reserved. GED is a registered trademark of the American Council on Education.
							All other trademarks are the property of their respective owners.
						</p>
                        <!-- 21374 -->
					</div> <!-- /cya -->

					<div class="clearboth"></div>

				</div><!-- /.wrap-fcs -->

				<hr />

				<div id="ets-lll">
					<img src="${pageContext.request.contextPath}/commonweb/img/ets-lll_140x55.gif" alt="ETS. Listening. Learning. Leading" width="140" height="55" />
				</div>

			</footer>
