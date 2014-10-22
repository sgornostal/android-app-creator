<?php
require 'androidbuild.php';
require_once 'aws/sdk.class.php';
$instance_id  = 'i-70d59444';

$options = array(
        'key' => 'AKIAIRBJC34TDGDOMCVA',
        'secret' => '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',
	'region' => 'us-west-2'
);
$ec2 = new AmazonEC2($options);
$ec2->set_region(AmazonEC2::REGION_OREGON);
$ec2->disable_ssl();
$Status = getStatus($ec2, $instance_id);
if($Status =="") {
  $startInstance = $ec2->startInstances($instance_id) ;
}
echo "started!";
?>