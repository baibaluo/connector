<?php
	header("Content-Type:text/html;charset=UTF-8");
	//$num_iid="9956631185";
	require_once dirname(__FILE__) . DIRECTORY_SEPARATOR .'../lib/TopSdk.php';
	$c = new TopClient;

	$my_conf = parse_ini_file(dirname(__FILE__) . DIRECTORY_SEPARATOR ."../conf/MySQL.ini", true);
	//echo "Use these account \n";
	
	//print_r($my_conf);
	//sleep(1000);
		
	//while (true)
	//{
		
	    /*找到要抓取的供应商的昵称和归属（C店、商城、其他的接口）*/
		$link_sup = mysql_connect($my_conf['MYSQL']['host'],$my_conf['MYSQL']['usr'] ,$my_conf['MYSQL']['passwd'])
	    	or die('Could not connect: ' . mysql_error());
		mysql_select_db('tb_shop') or die('Could not select database');
		mysql_query("set names latin1");
		$query_sup="select *from supply_dist where flag=0 or flag=1";
		$result_sup = mysql_query($query_sup) or die('Query failed: ' . mysql_error());
		$wait_time=0;
		$onsale=array();
		while ($row_sup = mysql_fetch_array($result_sup, MYSQL_BOTH))
		{	
			/*获取Sup商品列表信息*/
			if($row_sup['flag']==1)//其他的情况没有店铺的概念
				$query_supprd="select *from sup_item_info where t_nick='".$row_sup['dist_name']."' and nick='".$row_sup['sup_name']."' and newflag=1 and picflag=0";
			else
				$query_supprd="select *from sup_item_info where t_nick='".$row_sup['dist_name']."' and nick='".$row_sup['sup_name']."' and newflag=1 and picflag=0 and shopflag=0";
			
			echo $query_supprd."\n";
			$result_supprd = mysql_query($query_supprd) or die('Query failed: ' . mysql_error());
			
			
			$wait_time=$row_sup['wait_time'];
			while ($row_supprd = mysql_fetch_array($result_supprd, MYSQL_BOTH))
			{		
					//$onsale[$num_iid]="onsale";
					//图片是否上传成功。
				$query_seller_pic="select count(*) as pnum from picrelate where t_nick='".$row_sup['dist_name']."' and s_item_id=".$row_supprd['num_iid']." and upflag!=0";
				echo $query_seller_pic."\n";
				$result_seller_pic = mysql_query($query_seller_pic) or die('Query failed: ' . mysql_error());
				$row_seller_pic = mysql_fetch_array($result_seller_pic, MYSQL_BOTH);
				if($row_seller_pic['pnum']==0 || $row_sup['flag']==1)
				{
					
				
					$t_seller_cids=",";
					if($row_supprd['seller_cids']!="" && $row_supprd['seller_cids']!=NULL )
					{
						$seller_cids_arr=split(",",$row_supprd['seller_cids']);
						
						foreach ($seller_cids_arr as $seller_cid)
						{
							if($seller_cid!="")
							{
								$query_seller_cids="select t_cid from shopcat where t_nick='".$row_sup['dist_name']."' and s_nick='".$row_sup['sup_name']."' and s_cid='".$seller_cid."'";
								$result_seller_cids = mysql_query($query_seller_cids) or die('Query failed: ' . mysql_error());
								$row_seller_cids = mysql_fetch_array($result_seller_cids, MYSQL_BOTH);
								$t_seller_cids=$t_seller_cids.$row_seller_cids['t_cid'].",";
							}
						}
						//echo $t_seller_cids;
						
					}
					
					
					$num_rows=0;
					$query_sel="select *  from dist_item_info  where nick='".$row_sup['dist_name']."' and s_nick='".$row_sup['sup_name']."' and s_num_iid=".$row_supprd['num_iid'];
					$sel_result = mysql_query($query_sel) or die('Query failed: ' . mysql_error());
					$num_rows = mysql_num_rows($sel_result);
					if($num_rows>0)
					{
						//$new_desc="";
						//taobao的要有规则desc的替换方式。
						if($row_sup['flag']==0)
						{
							//$add_desc='<TABLE border=0 cellSpacing=0 cellPadding=0 width=750><TBODY><TR><TD><IMG src="http://img02.taobaocdn.com/imgextra/i2/710939814/T2tn04XoRbXXXXXXXX_!!710939814.jpg"></TD></TR></TBODY></TABLE>';
							$prd_desc=$row_supprd['prd_desc'];
							$get_desc_pic_sql="select *from picrelate where  t_nick='".$row_sup['dist_name']."' and s_nick='".$row_sup['sup_name']."' and s_pic_flag=1 and s_item_id=".$row_supprd['num_iid'];
							$result_get_desc_pic = mysql_query($get_desc_pic_sql) or die('Query failed: ' . mysql_error());
						
							while ($row_get_desc_pic = mysql_fetch_array($result_get_desc_pic, MYSQL_BOTH))
							{
								$prd_desc = str_replace($row_get_desc_pic['s_pic_url'],$row_get_desc_pic['t_item_pic_url']."?v=1", $prd_desc);
						 		echo "Replace ".$row_get_desc_pic['s_pic_url']." to ".$row_get_desc_pic['t_item_pic_url']."\n";		
							}						
							$prd_desc=$add_desc.$prd_desc;
							
							$get_prdid_sql="select num_iid,s_num_iid from dist_item_info";
							$result_get_prdid_sql = mysql_query($get_prdid_sql) or die('Query failed: ' . mysql_error());
							
							while ($row_get_prdid = mysql_fetch_array($result_get_prdid_sql, MYSQL_BOTH))
							{
								$prd_desc = str_replace($row_get_prdid['s_num_iid'],$row_get_prdid['num_iid'], $prd_desc);
						 		echo "Replace ".$row_get_prdid['s_num_iid']." to ".$row_get_prdid['num_iid']."\n";		
							}
						}
						else //发往京东的暂时先用taobao的方式。
						{
							$prd_desc=$row_supprd['prd_desc'];
							
						}
						
						
						
						$query_up="update dist_item_info set nick='".$row_sup['dist_name'].
							"',sku='".$row_supprd['sku']."',props_name='".$row_supprd['props_name'].
							"',promoted_service='".$row_supprd['promoted_service']."',is_lightning_consignment=".$row_supprd['is_lightning_consignment'].
							", cid=".$row_supprd['cid'].",seller_cids='".$t_seller_cids."',props='".$row_supprd['props'].
							"',input_pids='".$row_supprd['input_pids']."', input_str='".$row_supprd['input_str']."',num=".$row_supprd['num'].",valid_thru=".$row_supprd['valid_thru'].
							",price=".$row_supprd['price'].",post_fee=".$row_supprd['post_fee'].", express_fee=".$row_supprd['express_fee'].",ems_fee=".$row_supprd['ems_fee'].
							",has_discount=".$row_supprd['has_discount'].",freight_payer='".$row_supprd['freight_payer'].
							"', has_invoice=".$row_supprd['has_invoice'].",has_warranty=".$row_supprd['has_warranty'].
							", approve_status='".$row_supprd['approve_status'].
							"',auction_point=".$row_supprd['auction_point'].
							", property_alias='".$row_supprd['property_alias'].
							"', outer_id='".$row_supprd['outer_id']."', is_virtual=".$row_supprd['is_virtual'].
							",video='".$row_supprd['video']."',after_sale_id=".$row_supprd['after_sale_id'].
							",sell_promise=".$row_supprd['sell_promise'].",prd_desc='".$prd_desc."' where nick='".$row_sup['dist_name']."' and s_nick='".$row_sup['sup_name']."' and s_num_iid=".$row_supprd['num_iid'];						
						echo $query_up."\n";
										
						$up_result = mysql_query($query_up) or die('Query failed: ' . mysql_error());
						echo "Update ".$row_supprd['num_iid']." Data Success!! \n";
						$affected_rows=mysql_affected_rows();
						if($affected_rows>0)
						{
							$up_sup_flag="update dist_item_info set flag=1 where  nick='".$row_sup['dist_name']."' and s_nick='".$row_sup['sup_name']."' and s_num_iid=".$row_supprd['num_iid'];
							$up_sup_result = mysql_query($up_sup_flag) or die('Query failed: ' . mysql_error());
							echo "Update ".$row_supprd['num_iid']." Flag Success!! \n";
						}
						
					}
					else 
					{
						/*对新插入的数据进行DESC的PIC替换*/
							if($row_sup['flag']==0)//tb往TB的暂时按次规则替换
							{							
								$new_desc="";
								//$add_desc='<TABLE border=0 cellSpacing=0 cellPadding=0 width=750><TBODY><TR><TD><IMG src="http://img02.taobaocdn.com/imgextra/i2/710939814/T2tn04XoRbXXXXXXXX_!!710939814.jpg"></TD></TR></TBODY></TABLE>';						
								$prd_desc=$row_supprd['prd_desc'];
								$get_desc_pic_sql="select *from picrelate where  t_nick='".$row_sup['dist_name']."' and s_nick='".$row_sup['sup_name']."' and s_pic_flag=1 and s_item_id=".$row_supprd['num_iid'];
								$result_get_desc_pic = mysql_query($get_desc_pic_sql) or die('Query failed: ' . mysql_error());
								
								while ($row_get_desc_pic = mysql_fetch_array($result_get_desc_pic, MYSQL_BOTH))
								{
									$prd_desc = str_replace($row_get_desc_pic['s_pic_url'],$row_get_desc_pic['t_item_pic_url']."?v=1", $prd_desc);
							 		echo "Replace ".$row_get_desc_pic['s_pic_url']." to ".$row_get_desc_pic['t_item_pic_url']."\n";		
								}
								$prd_desc=$add_desc.$prd_desc;
								
								$get_prdid_sql="select num_iid,s_num_iid from dist_item_info";
								$result_get_prdid_sql = mysql_query($get_prdid_sql) or die('Query failed: ' . mysql_error());
								
								while ($row_get_prdid = mysql_fetch_array($result_get_prdid_sql, MYSQL_BOTH))
								{
									$prd_desc = str_replace($row_get_prdid['s_num_iid'],$row_get_prdid['num_iid'], $prd_desc);
							 		echo "Replace ".$row_get_prdid['s_num_iid']." to ".$row_get_prdid['num_iid']."\n";		
								}
							}
							else //发往京东先上架再替换
							{
								$prd_desc=$row_supprd['prd_desc'];
							}								
								
								/*执行插入动作。*/
								
								$query_insert="insert into dist_item_info (title,nick,detail_url,
									type,sku,props_name,created,promoted_service,is_lightning_consignment,
									is_fenxiao,cid,seller_cids,props,input_pids,input_str,pic_url,num,valid_thru,
									list_time,delist_time,stuff_status,location,price,post_fee,express_fee,
									ems_fee,has_discount,freight_payer,has_invoice,has_warranty,has_showcase,
									modified,increment,approve_status,product_id,auction_point,
									property_alias,outer_id,is_virtual,is_taobao,
									is_ex,is_timing,video,is_3D,score,volume,one_station,second_kill,
									auto_fill,violation,is_prepay,
									ww_status,wap_desc,wap_detail_url,after_sale_id,cod_postage_id,
									sell_promise,prd_desc,s_num_iid,s_nick)values('".$row_supprd['title']."','".$row_sup['dist_name']."','".$row_supprd['detail_url'].
									"','".$row_supprd['type']."','".$row_supprd['sku']."','".$row_supprd['props_name']."','".$row_supprd['created']."','".$row_supprd['promoted_service']."',".$row_supprd['is_lightning_consignment'].
									",".$row_supprd['is_fenxiao'].",".$row_supprd['cid'].",'".$t_seller_cids."','".$row_supprd['props']."','".$row_supprd['input_pids']."','".$row_supprd['input_str']."','".$row_supprd['pic_url']."',".$row_supprd['num'].",".$row_supprd['valid_thru'].
									",'".$row_supprd['list_time']."','".$row_supprd['delist_time']."','".$row_supprd['stuff_status']."','".$row_supprd['location']."',".$row_supprd['price'].",".$row_supprd['post_fee'].",".$row_supprd['express_fee'].
									",".$row_supprd['ems_fee'].",".$row_supprd['has_discount'].",'".$row_supprd['freight_payer']."',".$row_supprd['has_invoice'].",".$row_supprd['has_warranty'].",".$row_supprd['has_showcase'].
									",'".$row_supprd['modified']."','".$row_supprd['increment']."','".$row_supprd['approve_status']."',".$row_supprd['product_id'].",".$row_supprd['auction_point'].
									",'".$row_supprd['property_alias']."','".$row_supprd['outer_id']."',-1,-1".
									",-1,-1,'".$row_supprd['video']."',-1,-1,-1,-1,-1,-1,-1,-1,-1,'".$row_supprd['wap_desc']."','".$row_supprd['wap_detail_url']."',-1,-1,-1,'".$prd_desc."',".$row_supprd['num_iid'].",'".$row_sup['sup_name']."')" ;
						
					
					   $result_inert = mysql_query($query_insert) or die('Query failed: ' . mysql_error());
					}
					
					
					//更新供应商表的新产品 标记
					
					$query_up_sup="update sup_item_info set newflag=0 where t_nick='".$row_sup['dist_name']."' and nick='".$row_sup['sup_name']."' and num_iid=".$row_supprd['num_iid'];
				
					$result_up_sup = mysql_query($query_up_sup) or die('Query failed: ' . mysql_error());
					echo "End No.".$row_supprd['num_iid']." Success!!\n";					
					sleep(2);
			
				}
			}

			
			
		}
		mysql_close();
		echo "Begin Sleep!!";
		//sleep(600);
		
	//}
	
	
	function Rules($resp,$s_pic_url)
	{
	
					/********
					if(strlen($resp->item->title)==0)
						$resp->item->title="";
					
					if(strlen(	$resp->item->num_iid)==0)
						$resp->item->num_iid=-1;
				
					if(strlen(	$resp->item->nick)==0)
						$resp->item->nick="";
				
					if(strlen(	$resp->item->detail_url)==0)
						$resp->item->detail_url="";
				
					if(strlen(	$resp->item->type)==0)
						$resp->item->type="";
					
					
					$resp->item->skus=json_encode($resp->item->skus);
					
					if(strlen(	$resp->item->props_name)==0)
						$resp->item->props_name="";
				
					if(strlen(	$resp->item->created)==0)
						$resp->item->created="";
				
					if(strlen(	$resp->item->promoted_service)==0)
						$resp->item->promoted_service=-1;
				
					if(strlen(	$resp->item->is_lightning_consignment)==0)
						$resp->item->is_lightning_consignment=-1;
				
					if(strlen(	$resp->item->is_fenxiao)==0)
						$resp->item->is_fenxiao=-1;
				
					if(strlen(	$resp->item->cid)==0)
						$resp->item->cid=-1;
				
					if(strlen(	$resp->item->seller_cids)==0)
						$resp->item->seller_cids="";
				
					if(strlen(	$resp->item->props)==0)
						$resp->item->props="";
				
					if(strlen(	$resp->item->input_pids)==0)
						$resp->item->input_pids="";
				
					if(strlen(	$resp->item->input_str)==0)
						$resp->item->input_str="";
				
					if(strlen(	$resp->item->pic_url)==0)
						$resp->item->pic_url="";
				
					if(strlen(	$resp->item->num)==0)
						$resp->item->num=-1;
				
					if(strlen(	$resp->item->valid_thru)==0)
						$resp->item->valid_thru=-1;
				
					if(strlen(	$resp->item->list_time)==0)
						$resp->item->list_time="";
				
					if(strlen(	$resp->item->delist_time)==0)
						$resp->item->delist_time="";
				
					if(strlen(	$resp->item->stuff_status)==0)
						$resp->item->stuff_status="";
				
					
					
					$resp->item->location=json_encode($resp->item->location);
					
					if(strlen(	$resp->item->price)==0)
						$resp->item->price=-1;
				
					if(strlen(	$resp->item->post_fee)==0)
						$resp->item->post_fee=-1;
				
					if(strlen(	$resp->item->express_fee)==0)
						$resp->item->express_fee=-1;
				
					if(strlen(	$resp->item->ems_fee)==0)
						$resp->item->ems_fee=-1;
				
					if(strlen(	$resp->item->has_discount)==0)
						$resp->item->has_discount=-1;
				
					if(strlen(	$resp->item->freight_payer)==0)
						$resp->item->freight_payer="";
				
					if(strlen(	$resp->item->has_invoice)==0)
						$resp->item->has_invoice=-1;
				
					if(strlen(	$resp->item->has_warranty)==0)
						$resp->item->has_warranty=-1;
				
					if(strlen(	$resp->item->has_showcase)==0)
						$resp->item->has_showcase=-1;
				
					if(strlen(	$resp->item->modified)==0)
						$resp->item->modified="";
				
					if(strlen(	$resp->item->increment)==0)
						$resp->item->increment="";
				
					if(strlen(	$resp->item->approve_status)==0)
						$resp->item->approve_status="";
				
					if(strlen(	$resp->item->postage_id)==0)
						$resp->item->postage_id=-1;
				
					if(strlen(	$resp->item->product_id)==0)
						$resp->item->product_id=-1;
				
					if(strlen(	$resp->item->auction_point)==0)
						$resp->item->auction_point=-1;
				
					if(strlen(	$resp->item->property_alias)==0)
						$resp->item->property_alias="";
					
					$resp->item->item_imgs=json_encode($resp->item->item_imgs);
					
					$resp->item->prop_imgs=json_encode($resp->item->prop_imgs);
		
					
					if(strlen(	$resp->item->outer_id)==0)
						$resp->item->outer_id="";
				
					if(strlen(	$resp->item->is_virtual)==0)
						$resp->item->is_virtual=-1;
				
					if(strlen(	$resp->item->is_taobao)==0)
						$resp->item->is_taobao=-1;
				
					if(strlen(	$resp->item->is_ex)==0)
						$resp->item->is_ex=-1;
				
					if(strlen(	$resp->item->is_timing)==0)
						$resp->item->is_timing=-1;
				
					if(strlen(	$resp->item->video)==0)
						$resp->item->video="";
				
					if(strlen(	$resp->item->is_3D)==0)
						$resp->item->is_3D=-1;
				
					if(strlen(	$resp->item->score)==0)
						$resp->item->score=-1;
				
					if(strlen(	$resp->item->volume)==0)
						$resp->item->volume=-1;
				
					if(strlen(	$resp->item->one_station)==0)
						$resp->item->one_station=-1;
				
					if(strlen(	$resp->item->second_kill)==0)
						$resp->item->second_kill="";
				
					if(strlen(	$resp->item->auto_fill)==0)
						$resp->item->auto_fill="";
				
					if(strlen(	$resp->item->violation)==0)
						$resp->item->violation=-1;
				
					if(strlen(	$resp->item->is_prepay)==0)
						$resp->item->is_prepay=-1;
				
					if(strlen(	$resp->item->ww_status)==0)
						$resp->item->ww_status=-1;
				
					if(strlen(	$resp->item->wap_desc)==0)
						$resp->item->wap_desc="";
				
					if(strlen(	$resp->item->wap_detail_url)==0)
						$resp->item->wap_detail_url="";
				
					if(strlen(	$resp->item->after_sale_id)==0)
						$resp->item->after_sale_id=-1;
				
					if(strlen(	$resp->item->cod_postage_id)==0)
						$resp->item->cod_postage_id=-1;
				
					if(strlen(	$resp->item->sell_promise)==0)
						$resp->item->sell_promise=-1;
						
					if(strlen(	$resp->item->desc)==0)
						$resp->item->desc="";	*/	
	}	
?>
