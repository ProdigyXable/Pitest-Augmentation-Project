<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../style.css">
</head>
<body>

<h1>Pit Test Coverage Report</h1>
<h2>Package Summary</h2>
<h3>$packageData.packageName$</h3>
<table>
    <thead>
        <tr>
            <th>Number of Classes</th>
            <th>Line Coverage</th>
            <th>Mutation Coverage</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>$packageData.totals.numberOfFiles$</td>
            <td>$packageData.totals.lineCoverage$% <div class="coverage_bar"><div class="coverage_complete width-$packageData.totals.lineCoverage$"></div><div class="coverage_legend">$packageData.totals.numberOfLinesCovered$/$packageData.totals.numberOfLines$</div></div></td>
            <td>$packageData.totals.mutationCoverage$% <div class="coverage_bar"><div class="coverage_complete width-$packageData.totals.mutationCoverage$"></div><div class="coverage_legend">$packageData.totals.numberOfMutationsDetected$/$packageData.totals.numberOfMutations$</div></div></td>
        </tr>
    </tbody>
</table>


<h3>Breakdown by Class</h3>
<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Line Coverage</th>
            <th>Mutation Coverage</th>
        </tr>
    </thead>
    <tbody>
$packageData.summaryData:{ summary | 
        <tr>
            <td><a href="./$summary.fileName$.html">$summary.fileName$</a></td>
            <td><div class="coverage_percentage">$summary.totals.lineCoverage$% </div><div class="coverage_bar"><div class="coverage_complete width-$summary.totals.lineCoverage$"></div><div class="coverage_legend">$summary.totals.numberOfLinesCovered$/$summary.totals.numberOfLines$</div></div></td>
            <td><div class="coverage_percentage">$summary.totals.mutationCoverage$% </div><div class="coverage_bar"><div class="coverage_complete width-$summary.totals.mutationCoverage$"></div><div class="coverage_legend">$summary.totals.numberOfMutationsDetected$/$summary.totals.numberOfMutations$</div></div></td>
        </tr>
}$
     </tbody>
</table>
<br/>


$errors : { error | 
<div class='unit'>
 $error$
</div>
<br/>
}$

<hr/>

Report generated by <a href='http://pitest.org'>PIT</a> 1.2.5-SNAPSHOT

</body>
</html>